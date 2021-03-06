import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "czesc_samochodowa")
public class CzescSamochodowa implements Serializable {

    private static final long serialVersionUID = -30710L;

    @Column (name = "id_czesci")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_czesci;

    @Column (name = "nazwa_czesci")
    private String nazwa_czesci;

    @Column (name = "numer_seryjny")
    private long numer_seryjny;

    @Column (name = "cena")
    private float cena;

    @Column (name = "ilosc")
    private int ilosc;

    @Column (name = "dostepnosc")
    private String dostepnosc;

    @ManyToMany(mappedBy = "CzescSamochodowa")
    private List<Zamowienie> zamowienie = new ArrayList<>();

/*
    @ManyToOne
    @JoinColumn(name = "id_zawartosc",updatable = false)//(name = "id_zawartosc")
    private ZawartoscZamowienia zawartosc_zamowienia;
*/

/*
    @OneToMany(mappedBy = "czesc_samochodowa")
    private List<ZawartoscZamowienia> zawartosc_zamowienia;
*/


    @ManyToOne //(cascade = { CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn (name = "id_kat")
    private Kategoria kategoria;

    public CzescSamochodowa ( String nazwa_czesci , long numer_seryjny , float cena , int ilosc , String dostepnosc ) {
        this.nazwa_czesci = nazwa_czesci;
        this.numer_seryjny = numer_seryjny;
        this.cena = cena;
        this.ilosc = ilosc;
        this.dostepnosc = dostepnosc;
    }

    public CzescSamochodowa(){}

    public int getId_czesci () {
        return id_czesci;
    }

    public void setId_czesci ( int id_czesci ) {
        this.id_czesci = id_czesci;
    }

    public String getNazwa_czesci () {
        return nazwa_czesci;
    }

    public void setNazwa_czesci ( String nazwa_czesci ) {
        this.nazwa_czesci = nazwa_czesci;
    }

    public long getNumer_seryjny () {
        return numer_seryjny;
    }

    public void setNumer_seryjny ( long numer_seryjny ) {
        this.numer_seryjny = numer_seryjny;
    }

    public float getCena () {
        return cena;
    }

    public void setCena ( float cena ) {
        this.cena = cena;
    }

    public int getIlosc () {
        return ilosc;
    }

    public void setIlosc ( int ilosc ) {
        this.ilosc = ilosc;
    }

    public String getDostepnosc () {
        return dostepnosc;
    }

    public void setDostepnosc ( String dostepnosc ) {
        this.dostepnosc = dostepnosc;
    }

    public Kategoria getKategoria () {
        return kategoria;
    }

    public void setKategoria ( Kategoria kategoria ) {
        this.kategoria = kategoria;
    }

    public List<Zamowienie> getZamowienie () {
        return zamowienie;
    }

    public void setZamowienie ( List<Zamowienie> zamowienie ) {
        this.zamowienie = zamowienie;
    }

    @Override
    public String toString () {
        return "CzescSamochodowa{" +
                "id_czesci=" + id_czesci +
                ", nazwa_czesci='" + nazwa_czesci + '\'' +
                ", numer_seryjny=" + numer_seryjny +
                ", cena=" + cena +
                ", ilosc=" + ilosc +
                ", dostepnosc=" + dostepnosc +
                ", kategoria=" + kategoria +
                '}';
    }
}
