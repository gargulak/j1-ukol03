package cz.czechitas.ukol3.model;

public class Disk {
    private long kapacita;
    private long vyuziteMisto;

    public long getKapacita() {
        return kapacita;
    }

    public void setKapacita(long kapacita) {
        this.kapacita = kapacita;
    }

    public long getVyuziteMisto() {
        return vyuziteMisto;
    }

    public void setVyuziteMisto(long vyuziteMisto) {
        if (vyuziteMisto > getKapacita()) {
            System.err.println("Disk je plny. Nelze vyuzit dalsi misto.");
        } else {
            this.vyuziteMisto = Math.max(vyuziteMisto, 0);
        }
    }

    public long getVolneMisto() {
        return getKapacita() - getVyuziteMisto();
    }

    @Override
    public String toString() {
        return "Disk{kapacita=" + kapacita + "B, vyuziteMisto=" + vyuziteMisto + "B}";
    }
}
