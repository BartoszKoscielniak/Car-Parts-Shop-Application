import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "czesc_samochodowa")
public class CarParts implements Serializable {

    private static final long serialVersionUID = -30710L;

    @Column (name = "id_czesci")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_part;

    @Column (name = "nazwa_czesci")
    private String part_name;

    @Column (name = "numer_seryjny")
    private long serial_number;

    @Column (name = "cena")
    private float price;

    @Column (name = "ilosc")
    private int amount;

    @Column (name = "dostepnosc")
    private String availability;

    @ManyToMany(mappedBy = "CarParts")
    private List<Order> order = new ArrayList<>();

    @ManyToOne
    @JoinColumn (name = "id_kat")
    private Category category;

    public CarParts(String part_name, long serial_number, float price, int amount, String availability) {
        this.part_name = part_name;
        this.serial_number = serial_number;
        this.price = price;
        this.amount = amount;
        this.availability = availability;
    }

    public CarParts(){}

    public int getId_part() {
        return id_part;
    }

    public void setId_part(int id_czesci ) {
        this.id_part = id_czesci;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String nazwa_czesci ) {
        this.part_name = nazwa_czesci;
    }

    public long getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(long numer_seryjny ) {
        this.serial_number = numer_seryjny;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float cena ) {
        this.price = cena;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int ilosc ) {
        this.amount = ilosc;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String dostepnosc ) {
        this.availability = dostepnosc;
    }

    public Category getKategoria () {
        return category;
    }

    public void setKategoria ( Category category) {
        this.category = category;
    }

    public List<Order> getZamowienie () {
        return order;
    }

    public void setZamowienie ( List<Order> order) {
        this.order = order;
    }

    @Override
    public String toString () {
        return "CzescSamochodowa{" +
                "id_czesci=" + id_part +
                ", nazwa_czesci='" + part_name + '\'' +
                ", numer_seryjny=" + serial_number +
                ", cena=" + price +
                ", ilosc=" + amount +
                ", dostepnosc=" + availability +
                ", kategoria=" + category +
                '}';
    }
}
