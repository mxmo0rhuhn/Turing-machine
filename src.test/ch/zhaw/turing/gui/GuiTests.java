package ch.zhaw.turing.gui;

public class GuiTests {

    Character[] empty = new Character[0];

    Character[] char1_1 = new Character[] { '1' };

    Character[] char1_14 = new Character[] { '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1' };

    Character[] char1_15 = new Character[] { '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1' };

    Character[] char1_16 = new Character[] { '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1' };

    Character[] char0_1 = new Character[] { '0' };

    Character[] char0_14 = new Character[] { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' };

    Character[] char0_15 = new Character[] { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' };

    Character[] char0_16 = new Character[] { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' };

//    @Test
//    public void randomCombineTests() {
//        Character[] result = MaschineView.get30Chars(empty, 'B', empty);
//        compare("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB", result);
//
//        result = MaschineView.get30Chars(char1_1, 'B', char0_1);
//        compare("BBBBBBBBBBBBBB1B0BBBBBBBBBBBBBB", result);
//
//        result = MaschineView.get30Chars(char1_1, '5', char0_16);
//        compare("BBBBBBBBBBBBBB15000000000000000", result);
//        
//        result = MaschineView.get30Chars(char1_15, '0', char0_16);
//        compare("1111111111111110000000000000000", result);
//        
//        result = MaschineView.get30Chars(char1_14, '1', char0_16);
//        compare("B111111111111111000000000000000", result);
//        
//        result = MaschineView.get30Chars(char1_14, '0', char0_15);
//        compare("B111111111111110000000000000000", result);
//        
//        result = MaschineView.get30Chars(char1_14, '1', char0_14);
//        compare("B11111111111111100000000000000B", result);
//    }
//
//    private void compare(String string, Character[] result) {
//        if (result.length != 31) {
//            throw new AssertionError("Resultat muss 31 Zeichen lang sein..");
//        } else if (string.length() != 31) {
//            throw new AssertionError("String muss 31 Zeichen lang sein..");
//        }
//        char[] expected = string.toCharArray();
//        for (int i = 0; i < expected.length; i++) {
//            if (expected[i] != result[i].charValue()) {
//                throw new AssertionError("Expected [" + string + "] but got [" + toString(result) + "] instead!");
//            }
//        }
//    }

    private String toString(Character[] characters) {
        StringBuilder sb = new StringBuilder();
        for (Character c : characters) {
            sb.append(c);
        }
        return sb.toString();
    }
}
