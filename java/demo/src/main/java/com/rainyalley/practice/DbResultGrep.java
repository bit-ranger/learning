package com.rainyalley.practice;

import java.util.ArrayList;
import java.util.List;

public class DbResultGrep {

    private static String result =
            "| 3929212 |   10 |  76615220 |         33 |      2 | 1140000017071541 |\n" +
                    "| 3932317 |   10 |   3633643 |         33 |      2 | 1100000011366835 |\n" +
                    "| 3932634 |   10 |   6539270 |         33 |      2 | 1100000011368576 |\n" +
                    "| 3934464 |   10 |   1127300 |         33 |      2 | 1100000011377746 |\n" +
                    "| 3934689 |   10 |  23300330 |         33 |      2 | 1100000011378810 |\n" +
                    "| 3936137 |   10 |    607871 |         33 |      2 | 1100000011387581 |\n" +
                    "| 3938330 |   10 |    158063 |         33 |      2 | 1100000011398871 |\n" +
                    "| 3942783 |   10 |   1930801 |         33 |      2 | 1100000011417630 |\n" +
                    "| 3946184 |   10 |  14407392 |         33 |      4 | 1100000011427685 |\n" +
                    "| 3952540 |   10 |  14407392 |         33 |      2 | 1100000011445298 |\n" +
                    "| 3955750 |   10 |  21066318 |         33 |      2 | 1100000011451725 |\n" +
                    "| 3955969 |   10 |    307909 |         33 |      2 | 1100000011452127 |\n" +
                    "| 3958438 |   10 |   3559434 |         33 |      2 | 1100000011456517 |\n" +
                    "| 3960921 |   10 |  33446041 |         33 |      2 | 1120000028344699 |\n" +
                    "| 3963577 |   10 |   4174385 |         33 |      2 | 1100000011471145 |\n" +
                    "| 3965022 |   10 |  31717145 |         33 |      4 | 1120000028369320 |\n" +
                    "| 3966822 |   10 |    228264 |         33 |      2 | 1100000011479165 |\n" +
                    "| 3968035 |   10 |  23649372 |         33 |      2 | 1100000011483600 |\n" +
                    "| 3968447 |   10 |   7543107 |         33 |      2 | 1100000011485021 |\n" +
                    "| 3969914 |   10 |  29499464 |         33 |      2 | 1100000011489052 |\n" +
                    "| 3970069 |   10 |   5464425 |         33 |      2 | 1100000011489395 |\n" +
                    "| 3977757 |   10 |   1104111 |         33 |      4 | 1100000011512235 |\n" +
                    "| 3978067 |   10 |   8169200 |         33 |      2 | 1100000011513318 |\n" +
                    "| 3983396 |   10 |  31167388 |         33 |      2 | 1120000028435053 |\n" +
                    "| 3985937 |   10 |    192159 |         33 |      4 | 1100000011538810 |\n" +
                    "| 3985944 |   10 |    192159 |         33 |      4 | 1100000011538841 |\n" +
                    "| 3986410 |   10 |  10897479 |         33 |      2 | 1100000011540967 |\n" +
                    "| 3986658 |   10 | 110705345 |         33 |      4 | 1160000003064114 |\n" +
                    "| 3987226 |   10 |  17094531 |         33 |      2 | 1100000011544231 |\n" +
                    "| 3988078 |   10 |   2426632 |         33 |      2 | 1100000011547396 |\n" +
                    "| 3989017 |   10 |   6192310 |         33 |      2 | 1100000011550620 |\n" +
                    "| 3989723 |   10 |    152613 |         33 |      2 | 1100000011552985 |\n" +
                    "| 3989840 |   10 |  16456612 |         33 |      2 | 1100000011553401 |\n" +
                    "| 3990488 |   10 |    584696 |         33 |      2 | 1100000011555802 |\n" +
                    "| 3993453 |   10 |  14855964 |         33 |      2 | 1100000011564467 |\n" +
                    "| 3993545 |   10 |   6909755 |         33 |      2 | 1100000011564728 |\n" +
                    "| 3994830 |   10 |  40283531 |         33 |      2 | 1120000028467167 |\n" +
                    "| 3994906 |   10 |   2671542 |         33 |      2 | 1100000011570822 |\n" +
                    "| 3995197 |   10 |  29968956 |         33 |      2 | 1100000011572220 |\n" +
                    "| 3996076 |   10 |  20281662 |         33 |      4 | 1100000011575522 |\n" +
                    "| 3996077 |   10 |  20281662 |         33 |      4 | 1100000011575523 |\n" +
                    "| 3998244 |   10 |  29266171 |         33 |      2 | 1100000011583888 |\n" +
                    "| 3998472 |   10 |   8332941 |         33 |      2 | 1100000011584487 |\n" +
                    "| 3998753 |   10 |   5480872 |         33 |      2 | 1100000011585264 |\n" +
                    "| 3998761 |   10 |   5480872 |         33 |      2 | 1100000011585292 |\n" +
                    "| 3998863 |   10 |   8336010 |         33 |      2 | 1100000011585624 |\n" +
                    "| 3998950 |   10 |   8336010 |         33 |      2 | 1100000011585846 |\n" +
                    "| 4002587 |   10 |  95998011 |         33 |      2 | 1160000003095698 |\n" +
                    "| 4004325 |   10 |   7824614 |         33 |      2 | 1100000011600554 |\n" +
                    "| 4005065 |   10 |  17600305 |         33 |      2 | 1100000011603694 |\n" +
                    "| 4005100 |   10 |  17600305 |         33 |      2 | 1100000011603785 |\n" +
                    "| 4005304 |   10 |  17600305 |         33 |      2 | 1100000011604485 |\n" +
                    "| 4008753 |   10 |  20486643 |         33 |      2 | 1100000011617287 |\n" +
                    "| 4014038 |   10 |    508015 |         33 |      2 | 1100000011633883 |\n" +
                    "| 4014039 |   10 |    508015 |         33 |      4 | 1100000011633884 |\n" +
                    "| 4023545 |   10 |   6024015 |         33 |      2 | 1100000011663066 |\n" +
                    "| 4024769 |   10 |  31352126 |         33 |      2 | 1120000028542886 |\n" +
                    "| 4027373 |   10 |   3798688 |         33 |      2 | 1100000011677490 |\n" +
                    "| 4028637 |   10 |  32127882 |         33 |      2 | 1120000028551758 |\n" +
                    "| 4030353 |   10 |  29418378 |         33 |      2 | 1100000011685568 |\n" +
                    "| 4040637 |   10 |    132482 |         33 |      2 | 1100000011716095 |\n" +
                    "| 4040983 |   10 |   3356815 |         33 |      2 | 1100000011716815 |";

    public static void main(String[] args) {
        int ci = 5;
        String[] rows = result.split("\n");

        List<String> column = new ArrayList<>();

        for (String row : rows) {
            String c = row.split("\\|")[ci + 1];
            column.add(c);
        }

        column.forEach(s -> System.out.println(s));

    }

}
