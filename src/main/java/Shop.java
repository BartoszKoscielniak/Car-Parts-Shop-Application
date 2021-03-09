import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "sklep")
public class Shop implements Serializable {

    private static final long serialVersionUID = -30700L;

    @Column (name = "id_sklepu", unique = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_shop;

    @Column (name = "miejscowosc")
    private String city;

    @Column (name = "obrot")
    private double turnover;

    @OneToMany(mappedBy = "shop")
    private  List<Workers> workersList;

    public Shop(String city, double obrot ) {
        this.city = city;
        this.turnover = obrot;
    }

    public Shop(){}

    public int getId_shop() {
        return id_shop;
    }

    public void setId_shop(int id_sklepu ) {
        this.id_shop = id_sklepu;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String miejscowosc ) {
        this.city = miejscowosc;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double obrot ) {
        this.turnover = obrot;
    }

    public static long getSerialVersionUID () {
        return serialVersionUID;
    }

    public List<Workers> getPracownik () {
        return workersList;
    }

    public void setPracownik ( List<Workers> workers) {
        workersList = workers;
    }

    @Override
    public String toString () {
        return "Sklep{" +
                "id_sklepu=" + id_shop +
                ", miejscowosc='" + city + '\'' +
                ", obrot=" + turnover +
                ", Pracownik=" + workersList +
                '}';
    }
}
