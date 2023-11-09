package phoneBookPackage;

import java.util.List;
import java.util.Scanner;

public interface Searchable {
    void printSearchResults(List<Profile> searchResults, Scanner scanner);
}
/*
 Denna interface har en metod, printSearchResults,
 för klasser som visar sökresultat.
 I koden använder både AdminUser och GuestUser
 detta interface för att skriva ut sökresultat.

Fördelar:
 Ett gemensamt interface tillåter olika användartyper att
hantera och visa sökresultat på egna sätt utan att påverka kodens struktur.
Polymorfism: Genom att implementera detta interface
kan både AdminUser och GuestUser
behandlas enhetligt vid behov, vilket ger flexibilitet.*/