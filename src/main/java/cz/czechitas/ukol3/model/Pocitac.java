package cz.czechitas.ukol3.model;

import java.util.ArrayList;
import java.util.List;

public class Pocitac {

    private boolean jeZapnuty;

    private Procesor cpu;
    private Pamet ram;
    private Disk pevnyDisk;
    private Disk druhyDisk;

    public boolean jeZapnuty() {
        return jeZapnuty;
    }

    public Procesor getCpu() {
        return cpu;
    }

    public void setCpu(Procesor cpu) {
        this.cpu = cpu;
    }

    public Pamet getRam() {
        return ram;
    }

    public void setRam(Pamet ram) {
        this.ram = ram;
    }

    public Disk getPevnyDisk() {
        return pevnyDisk;
    }

    public void setPevnyDisk(Disk pevnyDisk) {
        this.pevnyDisk = pevnyDisk;
    }

    public Disk getDruhyDisk() {
        return druhyDisk;
    }

    public void setDruhyDisk(Disk druhyDisk) {
        this.druhyDisk = druhyDisk;
    }

    public long getTotalFreeSpace() {
        long freeSpace = 0;
        if (pevnyDisk != null) {
            freeSpace += pevnyDisk.getVolneMisto();
        }
        if (druhyDisk != null) {
            freeSpace += druhyDisk.getVolneMisto();
        }
        return freeSpace;
    }

    public long getTotalOccupiedSpace() {
        long occupiedSpace = 0;
        if (pevnyDisk != null) {
            occupiedSpace += pevnyDisk.getVyuziteMisto();
        }
        if (druhyDisk != null) {
            occupiedSpace += druhyDisk.getVyuziteMisto();
        }
        return occupiedSpace;
    }

    public void zapniSe() {
        if (jeZapnuty()) {
            System.err.println("Pocitac nelze zapnout, protoze je zapnuty");
        } else if (hasValidConfiguration()) {
            jeZapnuty = true;
            System.out.println("Pocitac se zapnul");
        }
    }

    public void vypniSe() {
        if (jeZapnuty()) {
            jeZapnuty = false;
            System.out.println("Pocitac se vypnul");
        }
    }

    public boolean hasValidConfiguration() {
        List<String> missingComponents = new ArrayList<>(3);
        if (cpu == null) {
            missingComponents.add("CPU");
        }
        if (ram == null) {
            missingComponents.add("RAM");
        }
        if (pevnyDisk == null) {
            missingComponents.add("HDD");
        }
        if (missingComponents.isEmpty()) {
            return true;
        }
        System.err.println("Nasledujuce komponenty v pocitaci chybaju: " + String.join(", ", missingComponents));
        return false;
    }

    public void vytvorSouborOVelikosti(long velikost) {
        if (!jeZapnuty()) {
            System.err.println("S vypnutym pocitacom sa nedaju vytvarat subory");
            return;
        }
        if (isNegativeFileSize(velikost)) {
            System.err.println("Subor so zapornou velkostou nie je mozne vytvorit");
            return;
        }
        if (!isEnoughFreeSpace(velikost)) {
            System.err.println("Pocitac nema dostatok volneho miesta na pripojenych diskoch");
            return;
        }
        long writtenBytesCount = writeFileToDisk(pevnyDisk, velikost);
        writeFileToDisk(druhyDisk, velikost - writtenBytesCount);
    }

    private boolean isNegativeFileSize(long fileSize) {
        return fileSize < 0;
    }

    private boolean isEnoughFreeSpace(long requiredFreeSpace) {
        return requiredFreeSpace <= getTotalFreeSpace();
    }

    /**
     * @return Number of written bytes to the provided disk. The file might have been written only partially.
     */
    private long writeFileToDisk(Disk disk, long fileSize) {
        if (disk == null || fileSize == 0) {
            return 0;
        }
        long bytesToBeWritten = Math.min(disk.getVolneMisto(), fileSize);
        disk.setVyuziteMisto(disk.getVyuziteMisto() + bytesToBeWritten);
        return bytesToBeWritten;
    }

    public void vymazSouboryOVelikosti(long velikost) {
        if (!jeZapnuty()) {
            System.err.println("S vypnutym pocitacom sa nedaju mazat subory");
            return;
        }
        if (isNegativeFileSize(velikost)) {
            System.err.println("Subor so zapornou velkostou nie je mozne zmazat");
            return;
        }
        if (!isEnoughOccupiedSpace(velikost)) {
            System.err.println("Pozaduje sa zmazat vacsie mnozstvo dat nez je na diskoch ulozenych");
            return;
        }
        long removedBytesCount = removeFromDisk(druhyDisk, velikost);
        removeFromDisk(pevnyDisk, velikost - removedBytesCount);
    }

    private boolean isEnoughOccupiedSpace(long requiredFileSize) {
        return requiredFileSize <= getTotalOccupiedSpace();
    }

    /**
     * @return Actual number of bytes which were deleted from provided disk.
     */
    private long removeFromDisk(Disk disk, long fileSize) {
        if (disk == null || fileSize == 0) {
            return 0;
        }
        long bytesToBeRemoved = Math.min(disk.getVyuziteMisto(), fileSize);
        disk.setVyuziteMisto(disk.getVyuziteMisto() - bytesToBeRemoved);
        return bytesToBeRemoved;
    }

    @Override
    public String toString() {
        return "Pocitac{" +
                "jeZapnuty=" + jeZapnuty +
                ", cpu=" + cpu +
                ", ram=" + ram +
                ", pevnyDisk=" + pevnyDisk +
                (druhyDisk == null ? "" : ", druhyDisk=" + druhyDisk) +
                '}';
    }
}
