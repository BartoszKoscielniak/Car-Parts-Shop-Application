import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "zamowienie")
public class Zamowienie implements Serializable {

    private static final long serialVersionUID = -30740L;

    @Column (name = "id_zamowienia")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_zamowienia;

    @Column (name = "cena")
    private float cena;

    @Column (name = "nr_zamowienia")
    private int nr_zamowienia;

    @Column (name = "zreazlizowano")
    private String zrealizowano;

    @ManyToOne
    @JoinColumn (name = "id_prac")
    private Pracownik pracownik;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "zawartosc",
            joinColumns = { @JoinColumn(name = "id_zamowienia")},
            inverseJoinColumns = {@JoinColumn(name = "id_czesci")}
    )
    List<CzescSamochodowa> CzescSamochodowa = new ArrayList<>();

    public Zamowienie ( String zrealizowano ) {

    }

    public Zamowienie(){}

    public int getId_zamowienia () {
        return id_zamowienia;
    }

    public void setId_zamowienia ( int id_zamowienia ) {
        this.id_zamowienia = id_zamowienia;
    }

    public float getCena () {
        return cena;
    }

    public void setCena ( float cena ) {
        this.cena = cena;
    }

    public int getNr_zamowienia () {
        return nr_zamowienia;
    }

    public void setNr_zamowienia ( int nr_zamowienia ) {
        this.nr_zamowienia = nr_zamowienia;
    }

    public Pracownik getPracownik () {
        return pracownik;
    }

    public void setPracownik ( Pracownik pracownik ) {
        this.pracownik = pracownik;
    }

    public static long getSerialVersionUID () {
        return serialVersionUID;
    }

    public String getZrealizowano () {
        return zrealizowano;
    }

    public void setZrealizowano ( String zrealizowano ) {
        this.zrealizowano = zrealizowano;
    }

    public List<CzescSamochodowa> getCzescSamochodowa () {
        return CzescSamochodowa;
    }

    public void setCzescSamochodowa ( List<CzescSamochodowa> czescSamochodowa ) {
        CzescSamochodowa = czescSamochodowa;
    }
}
