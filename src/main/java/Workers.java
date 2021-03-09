import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "pracownik")
public class Workers implements Serializable {

    private static final long serialVersionUID = -30730L;

    @Column (name = "id_prac")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id_worker;

    @Column (name = "imie")
    private String name;

    @Column (name = "nazwisko")
    private String surname;

    @Column (name = "data_zatrudnienia")
    private String dateOfEmployment;

    @Column (name = "nr_telefonu")
    private long phoneNumber;

    @Column (name = "pesel")
    private long pesel;

    @Column (name = "zarobki")
    private double earings;

    @ManyToOne
    @JoinColumn (name = "id_sklepu", referencedColumnName = "id_sklepu")
    private Shop shop;

    @OneToMany(mappedBy = "workers",
            cascade = {CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<Order> orderList;

    public Workers(String name, String surname, String dateOfEmployment, long phoneNumber, long pesel , double earings) {
        this.id_worker = id_worker;
        this.name = name;
        this.surname = surname;
        this.dateOfEmployment = dateOfEmployment;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
        this.earings = earings;
    }

    public Workers(){}

    public int getId_worker() {
        return id_worker;
    }

    public String getName() {
        return name;
    }

    public void setName(String imie ) {
        this.name = imie;
    }

    public String getSurname() {
        return surname;
    }

    public String getDateOfEmployment() {
        return dateOfEmployment;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public long getPesel () {
        return pesel;
    }

    public double getEarings() {
        return earings;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString () {
        return "Pracownik{" +
                "id_prac=" + id_worker +
                ", imie='" + name + '\'' +
                ", nazwisko='" + surname + '\'' +
                ", data_zatrudnienia='" + dateOfEmployment + '\'' +
                ", nr_telefonu=" + phoneNumber +
                ", pesl=" + pesel +
                ", zarobki=" + earings +
                '}';
    }
}
