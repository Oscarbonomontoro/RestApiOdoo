package modelos;

import java.sql.Timestamp;

public class State {
    private int id;
    private int country_id;
    private int create_uid;
    private int write_uid;
    private String name;
    private String code;
    private Timestamp create_date;
    private Timestamp write_date;

    // Constructor para crear un nuevo estado
    public State(int id, int country_id, int create_uid, int write_uid, String name, String code, Timestamp create_date, Timestamp write_date) {
        this.id = id;
        this.country_id = country_id;
        this.create_uid = create_uid;
        this.write_uid = write_uid;
        this.name = name;
        this.code = code;
        this.create_date = create_date;
        this.write_date = write_date;
    }

    // Constructor para crear un nuevo estado, faltarian las fechas (null por el momento)
    public State(int id, int country_id, String name, String code) {
        this(id, country_id, 1, 1, name, code, null, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public int getCreate_uid() {
        return create_uid;
    }

    public void setCreate_uid(int create_uid) {
        this.create_uid = create_uid;
    }

    public int getWrite_uid() {
        return write_uid;
    }

    public void setWrite_uid(int write_uid) {
        this.write_uid = write_uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Timestamp create_date) {
        this.create_date = create_date;
    }

    public Timestamp getWrite_date() {
        return write_date;
    }

    public void setWrite_date(Timestamp write_date) {
        this.write_date = write_date;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", country_id=" + country_id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}