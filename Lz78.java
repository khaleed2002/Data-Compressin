import java.util.ArrayList;

class Scratch {
    static int searchOnArrayList(String search,ArrayList<String>a){
        if(a.contains(search)) {
            return a.indexOf(search);
        }
        return -1;
    }
    static String PrintTag(ArrayList<Integer>index,ArrayList<String>next_c){
        String tmp="",tmp2;
        for(int i=0;i<next_c.size();i++){
            tmp2=next_c.get(i);
            if(tmp2.length()!=0) {
                tmp += "<" + Integer.toString(index.get(i)) + "," + tmp2.charAt(tmp2.length() - 1) + ">";
            }
            else {
                tmp += "<" + Integer.toString(index.get(i)) + "," + ">";
            }
        }
        return tmp;
    }
    static String compress(String s){
        ArrayList<String>dictionary=new ArrayList<String>();
        String tmp="";
        int index1=0;
        dictionary.add("");//constant condition
        ArrayList<Integer>index=new ArrayList<Integer>();
        for(int i=0;i<s.length();i++){
            tmp+=s.charAt(i);
            if(searchOnArrayList(tmp,dictionary)==-1){//npt found on dictionary
                dictionary.add(tmp);
                index.add(index1);
                index1=0;
                tmp="";
            }
            else{
                index1=searchOnArrayList(tmp,dictionary);
                if(i==s.length()-1){
                    index.add(index1);
                    dictionary.add("");
                }
            }
        }
        dictionary.remove(0);
        String compressed=PrintTag(index,dictionary);
        return compressed;
    }
    static String decompress(String compressed){
        String tmp="";
        //ignore < > ,
        for(int i=0;i<compressed.length();i++){
            if(!(compressed.charAt(i)=='<'||compressed.charAt(i)=='>'||compressed.charAt(i)==',')){
                tmp+=compressed.charAt(i);
            }
        }
        compressed=tmp;
        tmp="";
        ArrayList<String>dictionary=new ArrayList<String>();
        dictionary.add("0");
        int x=0;
        String tmp3="";
        for (int i=0;i<compressed.length();i++){

            Boolean flag = Character.isDigit(compressed.charAt(i));
            if(flag){
                tmp3+=compressed.charAt(i);
                x=Integer.parseInt(tmp3);
                if(i == compressed.length() - 1){
                    tmp+=dictionary.get(x);
                }
            }
            else{
                if(x==0) {
                    tmp+=compressed.charAt(i);
                    String tmp2="";
                    tmp2+=compressed.charAt(i);
                    dictionary.add(tmp2);
                    tmp2="";
                }
                else{
                    String tmp2="";
                    x=Integer.parseInt(tmp3);
                    tmp+=dictionary.get(x)+compressed.charAt(i);//compressed.charAt(i) to search for it after that
                    tmp2+=dictionary.get(x)+compressed.charAt(i);
                    dictionary.add(tmp2);
                    tmp2="";

                }
                tmp3="";
            }
        }
        return tmp;
    }
    public static void main(String[] args) {
        String s="EEBEBEBBCBCBBBBBBBBBBBBBBBBBCBCBCBBBCEBBE";
        String compressed= compress(s);
        System.out.println(compressed);
        System.out.println(decompress(compressed));
        System.out.println();

    }
}
