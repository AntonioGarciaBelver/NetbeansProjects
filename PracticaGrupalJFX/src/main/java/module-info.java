module com.mycompany.loginfxml {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires lombok;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires java.base;
    

    opens com.mycompany.loginfxml to javafx.fxml;
    opens models;
    exports com.mycompany.loginfxml;
}
