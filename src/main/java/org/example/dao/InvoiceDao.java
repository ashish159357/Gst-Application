package org.example.dao;
import org.example.model.Invoice;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InvoiceDao {

    private static final String INSERT_INVOICE_SQL = "INSERT INTO invoices (invoice_id, amount, gst_rate, gst_amount) VALUES (?, ?, ?, ?)";
    private final DataSource dataSource;

    public InvoiceDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertInvoice(Invoice invoice) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INVOICE_SQL)) {

            preparedStatement.setDouble(1, invoice.getInvoiceId());
            preparedStatement.setDouble(2, invoice.getAmount());
            preparedStatement.setDouble(3, invoice.getGstRate());
            preparedStatement.setDouble(4, invoice.getGstAmount());

            int rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

