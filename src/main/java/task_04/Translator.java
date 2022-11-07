package task_04;

public class Translator {

    private String worldTranslator;

    public Translator(String worldTranslator) {
        this.worldTranslator = worldTranslator;
    }

    @Override
    public String toString() {
        return "Translator{" +
                "worldTranslator='" + worldTranslator + '\'' +
                '}';
    }
}
