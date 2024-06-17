# GST Calculation Application

This application calculates GST for invoices whenever a user posts an invoice to the API endpoint `localhost:8080/invoice/create`.

## API Endpoint

- **POST** `localhost:8080/invoice/create`

### Example JSON to POST:

```json
{
    "invoiceId": 2,
    "amount": 550,
    "gstRate": 18,
    "gstAmount": 0
}
```


## How It Works

1. **Posting Invoices:**
   - When a user posts an invoice JSON to the API endpoint `localhost:8080/invoice/create`, the application adds the invoice to a `ConcurrentLinkedQueue`.
   - The `ConcurrentLinkedQueue` is implemented as a Singleton class, ensuring that all invoice postings are handled through a single instance of the queue.

2. **Invoice Processing:**
   - The application includes an `InvoiceProcessor` class, which is also a Singleton. This class continuously polls invoices from the queue.
   - The `InvoiceProcessor` runs as a separate thread, consistently checking for new invoices in the queue.
   - When an invoice is found, the `InvoiceProcessor` creates a new thread to handle the invoice processing. This new thread calculates the GST for the invoice.
   - Each thread created by the `InvoiceProcessor` is submitted to a thread pool for execution. This ensures efficient use of system resources and allows for concurrent processing of multiple invoices.

3. **GST Calculation:**
   - Each thread calculates the GST amount for the invoice using the formula: `gstAmount = amount * gstRate / 100`.
   - The calculated GST amount is then updated in the invoice record.

4. **Database Update:**
   - After calculating the GST, the application updates the corresponding invoice record in the PostgreSQL database.


## Steps to Run the Application

1. **Set Up PostgreSQL Database:**
    - Ensure that PostgreSQL is installed and running on your system.
    - You can download PostgreSQL from the official website: [PostgreSQL Downloads](https://www.postgresql.org/download/)

2. **Create Database:**
    - Connect to your PostgreSQL server using a tool like `psql` or a database management tool like pgAdmin.
    - Create a database named `gstapp` by executing the following command:

   ```sql
   CREATE DATABASE gstapp;
   
3. **Set username and password of database in DatabaseConfig class**

4. **Create Table:**
    - To store the invoice data, you need to create a table in the `gstapp` database. Use the following SQL query to create the `invoices` table:

    ```sql
    CREATE TABLE public.invoices (
        invoice_id INT8 PRIMARY KEY,
        amount INT8 NOT NULL,
        gst_rate INT8 NOT NULL,
        gst_amount INT8 NOT NULL
    );

5. **Now just run Main class**

## Note

- **In logs you can see the counts of Successfully calculated Invoices**