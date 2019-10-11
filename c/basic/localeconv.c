#include <locale.h>
#include <stdio.h>

int main ()
{
   struct lconv * lc;

   setlocale(LC_MONETARY, "it_IT");
   lc = localeconv();
   printf("Local Currency Symbol: %s\n",lc->currency_symbol);
   printf("International Currency Symbol: %s\n",lc->int_curr_symbol);

   setlocale(LC_MONETARY, "en_US");
   lc = localeconv();
   printf("Local Currency Symbol: %s\n",lc->currency_symbol);
   printf("International Currency Symbol: %s\n",lc->int_curr_symbol);

   setlocale(LC_MONETARY, "en_GB");
   lc = localeconv();
   printf ("Local Currency Symbol: %s\n",lc->currency_symbol);
   printf ("International Currency Symbol: %s\n",lc->int_curr_symbol);

   printf("Decimal Point = %s\n", lc->decimal_point);
   
   return 0;
}
