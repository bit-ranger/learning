package com.rainyalley.practice;

import java.util.Arrays;
import java.util.List;

public class ImgGene {

    static List<String> list = Arrays.asList(
             "http://qn-act.qbcdn.com/c08bbd88a15434725b1bc0790e3dcd52b1934981-ece6-4cce-8f17-b6044fda8e2c"
            ,"http://qn-act.qbcdn.com/d584d87d25d690cdf65b5bf565bfba8ac22bc37b-993a-47dd-9892-873f9b0c3386"
            ,"http://qn-act.qbcdn.com/54e210723d1a1e1b3a4ab46162171d22e1d54a64-4905-4e98-884e-31b7480b2f63"
            ,"http://qn-act.qbcdn.com/a503a4f30cb086fcce0e6787afd17940c7ba9a53-32b0-469b-b2b2-3256c1030d75"
            ,"http://qn-act.qbcdn.com/8635744f0b585578cd2aeaeb677952b862378fce-9d69-480d-8825-0faa48cfad1b"
            ,"http://qn-act.qbcdn.com/8ac80ca3dd027d2cef6ebaf0c283472dff6a881a-c3d7-4ff6-920e-81962f2be039"
            ,"http://qn-act.qbcdn.com/001853cb6137f8e4d84d108b5f7b5c051d1e035c-de26-474c-a4c6-0b673325e4e1"
            ,"http://qn-act.qbcdn.com/bcb5d0343fd10c17450fc0f57ed8368a44b0d4da-3bd7-45d4-bd97-9ce2de9ef30d"
            ,"http://qn-act.qbcdn.com/fdd646bbf1322759b4dc9c62de596939e35e12e5-7757-40fc-bef2-781f7edf7bda"
            ,"http://qn-act.qbcdn.com/79991b1aebe0d16866d34f006f2013c63ba23cac-6195-424f-afbc-0a9d2d517948"
            ,"http://qn-act.qbcdn.com/cacedd6883829ebd58897e57b853e714f8f7b4c1-c75c-490d-a1bc-a2f999f79aec"
            ,"http://qn-act.qbcdn.com/d29308d03675c50d858f331e74ed96e6c1487869-60b2-46f5-8245-ee497e98c802"
            ,"http://qn-act.qbcdn.com/50ff90f0aec4e80dab829381ce417ad6b9e6545a-6d60-45f9-9011-fe19d724176b"
            ,"http://qn-act.qbcdn.com/2335943868457fef91eb102e8b438086d2087851-4488-4fd8-9116-24c380390f88"
            ,"http://qn-act.qbcdn.com/78bfd1514144aac74ff1381df615fdf1ad98be53-a92a-4a0f-bd9f-3f68308b8687"
            ,"http://qn-act.qbcdn.com/9caeeb39d0e8319aa7be57d1205e4705096627ce-a8f7-4d43-88cf-558d4974275f"
            ,"http://qn-act.qbcdn.com/30117c58a84321991c3173d639d5a9b499ba44bd-7eca-404a-b9a2-172e29d0e833"
            ,"http://qn-act.qbcdn.com/0eec1f0f8954bc6d68a4707d795d6ab22bdcfc8c-0889-4c23-b33a-221882b21775"
            ,"http://qn-act.qbcdn.com/954fb7af1ebf6714d45c41b471f30465ca1588a5-c6fc-41c7-b396-22c617162d66"
            ,"http://qn-act.qbcdn.com/21f02b1341ae89afb0ad00475762dea55513c771-21e9-4fad-a762-9dbe1ac38eb5"
            ,"http://qn-act.qbcdn.com/5c134d12a607f32fd63d73449a335f957fe3ea6c-4c07-4ac6-9397-c2a16865a752"
            ,"http://qn-act.qbcdn.com/0dd4c85c1e0265b4397057388ced39246141cffe-f8fb-480a-becb-6f55813cfd2d"
            ,"http://qn-act.qbcdn.com/bf0afcfcbb71fa06b0fe53903a51af945ae2c7f8-b6e3-4383-b85d-3749e77a1792"
            ,"http://qn-act.qbcdn.com/7d722749fa70830558a98b8b67e58a493d60e27e-a574-4ec0-8035-3822a3243ebc"
            ,"http://qn-act.qbcdn.com/1682fefebb0b36bad61b7f97195a106a216c1638-35f2-4cc8-ab5b-15f3b195deb4"
            ,"http://qn-act.qbcdn.com/90a2a7082dbf0ded231507c23f26ba8a4b193b44-0d6f-4c00-a819-fe674c4bf3a8"

    );

    public static void main(String[] args){
        StringBuilder description = new StringBuilder();
        for (String s : list) {
            description.append("<img src=\"");
            description.append(s);
            description.append("\"");
            description.append(" _xhe_src=\"");
            description.append(s);
            description.append("\"");
            description.append(" alt=\"\">");
        }
        System.out.println(description);
    }

}
