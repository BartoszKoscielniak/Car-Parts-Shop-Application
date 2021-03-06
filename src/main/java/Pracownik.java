import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "pracownik")
public class Pracownik implements Serializable {

    private static final long serialVersionUID = -30730L;

    @Column (name = "id_prac")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id_prac;

    @Column (name = "imie")
    private String imie;

    @Column (name = "nazwisko")
    private String nazwisko;

    @Column (name = "data_zatrudnienia")
    private String data_zatrudnienia;

    @Column (name = "nr_telefonu")
    private long nr_telefonu;

    @Column (name = "pesel")
    private long pesel;

    @Column (name = "zarobki")
    private double zarobki;

    @ManyToOne
    @JoinColumn (name = "id_sklepu", referencedColumnName = "id_sklepu")
    private Sklep sklep;

    @OneToMany(mappedBy = "pracownik",
            cascade = {CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<Zamowienie> zamowienieList;

    public Pracownik ( String imie , String nazwisko , String data_zatrudnienia , long nr_telefonu , long pesel , double zarobki ) {
        this.id_prac = id_prac;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.data_zatrudnienia = data_zatrudnienia;
        this.nr_telefonu = nr_telefonu;
        this.pesel = pesel;
        this.zarobki = zarobki;
    }

    public Pracownik(){}

    public int getId_prac () {
        return id_prac;
    }

    public void setId_prac ( int id_prac ) {
        this.id_prac = id_prac;
    }

    public String getImie () {
        return imie;
    }

    public void setImie ( String imie ) {
        this.imie = imie;
    }

    public String getNazwisko () {
        return nazwisko;
    }

    public void setNazwisko ( String nazwisko ) {
        this.nazwisko = nazwisko;
    }

    public String getData_zatrudnienia () {
        return data_zatrudnienia;
    }

    public void setData_zatrudnienia ( String data_zatrudnienia ) {
        this.data_zatrudnienia = data_zatrudnienia;
    }

    public long getNr_telefonu () {
        return nr_telefonu;
    }

    public void setNr_telefonu ( long nr_telefonu ) {
        this.nr_telefonu = nr_telefonu;
    }

    public long getPesel () {
        return pesel;
    }

    public void setPesel ( long pesl ) {
        this.pesel = pesl;
    }

    public double getZarobki () {
        return zarobki;
    }

    public void setZarobki ( double zarobki ) {
        this.zarobki = zarobki;
    }

    public static long getSerialVersionUID () {
        return serialVersionUID;
    }

    public Sklep getSklep () {
        return sklep;
    }

    public void setSklep ( Sklep sklep ) {
        this.sklep = sklep;
    }

    @Override
    public String toString () {
        return "Pracownik{" +
                "id_prac=" + id_prac +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", data_zatrudnienia='" + data_zatrudnienia + '\'' +
                ", nr_telefonu=" + nr_telefonu +
                ", pesl=" + pesel +
                ", zarobki=" + zarobki +
                '}';
    }
}
