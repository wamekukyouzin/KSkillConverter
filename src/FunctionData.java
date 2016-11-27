import java.util.ArrayList;

/**
 * Created by wamek on 2016/11/26.
 */
public class FunctionData {
    public int number = 0;
    public ArrayList<String> skillList = new ArrayList<>();
    private static String sep = System.getProperty("line.separator");

    FunctionData (int number) {
        this.number = number;
    }

    public void addSkill(String skillName) {
        if (skillName == "") {
            System.out.println(number + "のスキルが空");
            return;
        }
        skillList.add(skillName);
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("@SKILL_INIT_" + number + "_GENERAL()" + sep);
        skillList.stream().map(ablName -> "CALL LEARN_SKILL_GENERAL_BY_NAME(" + number + ",\"" + ablName + "\")" + sep).forEach(stringBuffer::append);
        stringBuffer.append(sep);
        return stringBuffer.toString();
    }
}
