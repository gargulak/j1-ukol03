package cz.czechitas.ukol3;

import cz.czechitas.ukol3.model.Disk;
import cz.czechitas.ukol3.model.Pamet;
import cz.czechitas.ukol3.model.Pocitac;
import cz.czechitas.ukol3.model.Procesor;

/**
 * Spouštěcí třída celého programu
 */
public class HlavniProgram {

    public static void main(String... args) {

        Procesor cpu = new Procesor();
        cpu.setVyrobce("AMD");
        cpu.setRychlost(3_228_000_000L);
        System.out.println(cpu);

        Pamet ram = new Pamet();
        ram.setKapacita(16_000_000_000L);
        System.out.println(ram);

        // Pre zjednodusenie pocitania uvadzam nerealne male kapacity diskov
        Disk disk = new Disk();
        disk.setKapacita(1000L);
        disk.setVyuziteMisto(0L);
        System.out.println(disk);

        Disk druhyDisk = new Disk();
        druhyDisk.setKapacita(500L);
        druhyDisk.setVyuziteMisto(0L);
        System.out.println(druhyDisk);

        Pocitac pocitac = new Pocitac();
        pocitac.setCpu(cpu);
        pocitac.setRam(ram);
        pocitac.setPevnyDisk(disk);
        pocitac.setDruhyDisk(druhyDisk);

        pocitac.zapniSe();

        System.out.println(disk + "\n" + druhyDisk + "\n");

        pocitac.vytvorSouborOVelikosti(500L);
        System.out.println(disk + "\n" + druhyDisk + "\n");

        pocitac.vytvorSouborOVelikosti(600L);
        System.out.println(disk + "\n" + druhyDisk + "\n");

        pocitac.vytvorSouborOVelikosti(600L);
        System.out.println(disk + "\n" + druhyDisk + "\n");

        pocitac.vymazSouboryOVelikosti(1500L);
        System.out.println(disk + "\n" + druhyDisk + "\n");

        pocitac.vymazSouboryOVelikosti(99L);
        System.out.println(disk + "\n" + druhyDisk + "\n");

        pocitac.vymazSouboryOVelikosti(101L);
        System.out.println(disk + "\n" + druhyDisk + "\n");

        pocitac.vymazSouboryOVelikosti(400L);
        System.out.println(disk + "\n" + druhyDisk + "\n");

        pocitac.vymazSouboryOVelikosti(500L);
        System.out.println(disk + "\n" + druhyDisk + "\n");

        pocitac.vymazSouboryOVelikosti(1L);
        System.out.println(disk + "\n" + druhyDisk + "\n");

        pocitac.vypniSe();
    }
}
