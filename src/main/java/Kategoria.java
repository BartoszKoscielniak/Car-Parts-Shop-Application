import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "kategoria")
public class Kategoria implements Serializable {

    private static final long serialVersionUID = -30720L;

    @Column (name = "id_kat")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_kat;

    @Column (name = "nazwa_kategorii")
    private String nazwa_kategorii;

    @OneToMany(mappedBy = "kategoria", cascade = CascadeType.DETACH)
    private List<CzescSamochodowa> czescSamochodowaList;

    public Kategoria (String nazwa_kategorii ) {
        this.nazwa_kategorii = nazwa_kategorii;
    }

    public Kategoria(){}

    public int getId_kat () {
        return id_kat;
    }

    public void setId_kat ( int id_kat ) {
        this.id_kat = id_kat;
    }

    public String getNazwa_kategorii () {
        return nazwa_kategorii;
    }

    public void setNazwa_kategorii ( String nazwa_kategorii ) {
        this.nazwa_kategorii = nazwa_kategorii;
    }

    public List<CzescSamochodowa> getCzescSamochodowaList () {
        return czescSamochodowaList;
    }

    public void setCzescSamochodowaList ( List<CzescSamochodowa> czescSamochodowaList ) {
        this.czescSamochodowaList = czescSamochodowaList;
    }

    @Override
    public String toString () {
        return "Kategoria{" +
                "id_kat=" + id_kat +
                ", nazwa_kategorii='" + nazwa_kategorii + '\'' +
                ", czescSamochodowaList=" + czescSamochodowaList +
                '}';
    }

}
