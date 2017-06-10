import org.yakimovdenis.filereader.statisticsreader.charreaders.CharacterEqualiator;

public class CharacterEqualiatorProxy implements CharacterEqualiator {
    private CharacterEqualiator characterEqualiator;
    private int countedChars;

    CharacterEqualiatorProxy(CharacterEqualiator characterEqualiator) {
        this.characterEqualiator = characterEqualiator;
        this.countedChars = 0;
    }

    int getCountedChars() {
        return countedChars;
    }

    public CharacterEqualiator clone() {
        return this;
    }

    public String getUsedMethod() {
        return characterEqualiator.getUsedMethod();
    }

    public boolean matchesCondition(Character character) {
        boolean result = characterEqualiator.matchesCondition(character);
        if (result) countedChars++;
        return result;
    }
}
