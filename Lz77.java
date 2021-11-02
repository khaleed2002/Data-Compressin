import java.util.ArrayList;

class Scratch {
    /*
     * function to search for a char in str and return index if it founded
     * */
    static int stepsFromLast(char c, String s) {
        int steps = 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (c == s.charAt(i)) {
                return steps;
            } else {
                steps++;
            }
        }
        return -1;//if char is not in string s
    }

    /*
     * Print tag to be like doctor want <position,length,next_c>
     */
    static String printTag(ArrayList<Integer> v1, ArrayList<Integer> v2, ArrayList<Character> v3) {
        String result = "";
        for (int i = 0; i < v1.size(); i++) {
            String n_char = "";
            //because of error when there is null in next character
            if (v3.size() > i) {
                n_char = String.valueOf(v3.get(i));//transform char to string
            }
            //result is all tags
            result += "<" + String.valueOf(v1.get(i)) + "," + String.valueOf(v2.get(i)) + "," + n_char + ">";
        }
        return result;
    }

    static String compress(String s) {
        //initialise all array lists to store tags
        ArrayList<Integer> position = new ArrayList<Integer>(); //for position values
        ArrayList<Integer> length = new ArrayList<Integer>(); //for length values
        ArrayList<Character> nextChar = new ArrayList<Character>(); //for next character values
        //---------------------------------------------------------------------------

        String newStr = ""; //string to store the new string from s
        for (int i = 0; i < s.length(); i++) {
            String tmp = "";
            if (stepsFromLast(s.charAt(i), newStr) == -1)//char is not found
            {
                //if char is not found set position 0
                position.add(0);
                //if char is not found set length 0
                length.add(0);
                //if char is not found set next character
                nextChar.add(s.charAt(i));
                //set newStr that will be compared to see if char is founded after that or not
                newStr += s.charAt(i);
            } else { //if character founded ->
                String tmpGood;//because value for tmp will change ,and it used to be compared with string
                //to see if there are tmpGood (character) in newStr string or not
                tmp += s.charAt(i); //
                tmpGood = tmp; //string that will be compared in newStr
                int len = 1; //length will be at least (one)
                while (true) { //loop to see how long will be the repeated string (tmpGood)
                    i++;
                    if (s.length() > i) { //because of exception error because i==length
                        tmp += s.charAt(i);//at all this tmp will be added to newStr
                    }
                    //s.length()==i represent when there are last character ,so it will be added directly
                    if (newStr.lastIndexOf(tmp) == -1 || s.length() == i) //string not found in newStr ,or it is the last symbol
                    {
                        //loop is finished so add length now
                        length.add(len);
                        if (s.length() > i) {  //because of exception error
                            nextChar.add(s.charAt(i));
                        }
                        position.add(newStr.length() - newStr.lastIndexOf(tmpGood));
                        newStr += tmp;
                        break;
                    } else //string founded in newStr
                    {
                        tmpGood = tmp;
                        len++;
                        continue;
                    }

                }
            }
        }

        return printTag(position, length, nextChar);
    }

    /*
     * substr to take from index a length---
     * */
    static String substr(int index, int length, String s) {
        String s1 = "";
        for (int i = 0; i < length; i++) {
            s1 += s.charAt(index);
            index++;
        }
        return s1;
    }

    static String decompress(String compressed) {
        String tmp = "", compressed1 = "";
        ArrayList<Integer> position = new ArrayList<Integer>();
        ArrayList<Integer> length = new ArrayList<Integer>();
        ArrayList<Character> next_c = new ArrayList<Character>();
        for (int i = 0; i < compressed.length(); i++) {
            if (!(compressed.charAt(i) == '<' || compressed.charAt(i) == ',' || compressed.charAt(i) == '>')) {
                compressed1 += compressed.charAt(i);
            } else {
                compressed1 += "+"; //work as isolator from tag to another
            }
        }
        //now we have compressed1 like this +0+0+A++0+0+B++.... and so on
        for (int i = 0; i < compressed1.length(); i++) {
            if (compressed1.charAt(i) != '+') {
                tmp += compressed1.charAt(i);//to set a position or length or next_character
                continue;
            }
            if (compressed1.charAt(i) == '+' && tmp.length() != 0) // communicate with arraylists....tmp.length for exception error
            {
                if (position.size() <= length.size() && position.size() <= next_c.size()) {
                    position.add(Integer.parseInt(tmp));
                } else if (length.size() <= next_c.size()) {
                    length.add(Integer.parseInt(tmp));
                } else {
                    next_c.add(tmp.charAt(0));
                }
                tmp = "";
            }
        }
        for (int i = 0; i < position.size(); i++)//last thing after stored arraylists
        {
            if (position.get(i) == 0 && next_c.size() > i)//because of exception error
            {
                tmp += next_c.get(i);

            } else {
                int index = tmp.length() - position.get(i);
                tmp += substr(index, length.get(i), tmp);
                if (next_c.size() > i)//because of exception error
                    tmp += next_c.get(i);
            }
        }
        return tmp;
    }

    public static void main(String[] args) {
        String s = "EEBEBEBBCBCBBBBBBBBBBBBBBBBBCBCBCBBBCEBBE";
        String compressed = compress(s);
        System.out.println(compressed);
        System.out.println(decompress(compressed));
    }
}