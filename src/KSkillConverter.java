import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by wamek on 2016/11/26.
 */
public class KSkillConverter {
    private BufferedReader br;
    private Path filePath;
    KSkillConverter (String filePath){

        try {
            this.filePath = Paths.get(filePath);
            br = Files.newBufferedReader(this.filePath, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doIt() {
        TreeMap<Integer, FunctionData> funcDataMap = new TreeMap<>();
        br.lines().forEach(line -> {
            String[] sepLine = line.split(",");
            if (sepLine.length != 3) return;

            int charaNum = 0;

            try {
                charaNum = Integer.parseInt(sepLine[0]);
            } catch(NumberFormatException e) {
                return;
            }

            String skillName = sepLine[2];

            FunctionData functionData = funcDataMap.get(charaNum);
            if (functionData == null) {
                functionData = new FunctionData(charaNum);
                funcDataMap.put(charaNum, functionData);
            }
            functionData.addSkill(skillName);
        });
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //--------書き込み処理処理--------
        try {
            FileOutputStream fos = new FileOutputStream(filePath.getParent().toString() + "/INITIALIZE_GENERAL_SKILL.ERB");
            fos.write(0xef);
            fos.write(0xbb);
            fos.write(0xbf);
            PrintWriter printWriter = new PrintWriter(fos);

            funcDataMap.forEach((charanum, functionData) -> printWriter.write(functionData.toString()));
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //--------書き込み処理ここまで--------
    }
}
