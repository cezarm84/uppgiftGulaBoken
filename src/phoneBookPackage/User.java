package phoneBookPackage;

import java.util.Scanner;

public interface User extends Searchable {
    void menu();
}
/*
Detta interface har en metod, menu, som används av användarklasser för att visa menyalternativ.
Både AdminUser och GuestUser implementerar detta interface för att skapa sina egna menyer.
fördelar:
Genom att använda detta gemensamma interface kan olika användartyper
skapa sina egna menyer utan att påverka kodbasens struktur.
Eftersom både AdminUser och GuestUser
implementerar detta interface kan de behandlas enhetligt när det gäller att visa menyer.
Det ger flexibilitet */