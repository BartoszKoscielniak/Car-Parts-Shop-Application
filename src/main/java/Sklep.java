import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "sklep")
public class Sklep implements Serializable {

    private static final long serialVersionUID = -30700L;

    @Column (name = "id_sklepu", unique = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_sklepu;

    @Column (name = "miejscowosc")
    private String miejscowosc;

    @Column (name = "obrot")
    private double obrot;

    @OneToMany(mappedBy = "sklep")
    private  List<Pracownik> pracownikList;

    public Sklep ( String miejscowosc , double obrot ) {
        this.miejscowosc = miejscowosc;
        this.obrot = obrot;
    }

    public Sklep(){}

    public int getId_sklepu () {
        return id_sklepu;
    }

    public void setId_sklepu ( int id_sklepu ) {
        this.id_sklepu = id_sklepu;
    }

    public String getMiejscowosc () {
        return miejscowosc;
    }

    public void setMiejscowosc ( String miejscowosc ) {
        this.miejscowosc = miejscowosc;
    }

    public double getObrot () {
        return obrot;
    }

    public void setObrot ( double obrot ) {
        this.obrot = obrot;
    }

    public static long getSerialVersionUID () {
        return serialVersionUID;
    }

    public List<Pracownik> getPracownik () {
        return pracownikList;
    }

    public void setPracownik ( List<Pracownik> pracownik ) {
        pracownikList = pracownik;
    }

    @Override
    public String toString () {
        return "Sklep{" +
                "id_sklepu=" + id_sklepu +
                ", miejscowosc='" + miejscowosc + '\'' +
                ", obrot=" + obrot +
                ", Pracownik=" + pracownikList +
                '}';
    }
}
