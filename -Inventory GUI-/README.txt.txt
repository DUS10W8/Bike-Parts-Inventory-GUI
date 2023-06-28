Bike Store Parts Inventory GUI
This is a JavaFX application with a graphical user interface (GUI) that serves as a parts inventory system for a bike store. The GUI closely matches the layout and organization of the provided "Software 1 GUI Mock-Up" and includes the following forms:

Main form
Add Part form
Modify Part form
Add Product form
Modify Product form
User Interface
Prerequisites
JavaFX SDK or module (JavaFX is no longer included in the JDK API as of JDK 11)
Setup
Clone the repository to your local machine.
Ensure you have JavaFX properly set up.
Compile and run the application using your preferred IDE or command-line tools.
Main Form
The Main form consists of two panes: Parts pane and Products pane.

Parts Pane
The "Add" button opens the Add Part form.
The "Modify" button opens the Modify Part form.
The "Delete" button deletes the selected part from the Parts TableView. If a part is not deleted, a descriptive error message is displayed.
The user can search for parts by ID or name using the search field. The matching results are displayed in the Parts TableView. If no part is found, an error message is displayed. Clearing the search field repopulates the table with all available parts.
Products Pane
The "Add" button opens the Add Product form.
The "Modify" button opens the Modify Product form.
The "Delete" button deletes the selected product from the Products TableView. If a product is not deleted, a descriptive error message is displayed.
The user can search for products by ID or name using the search field. The matching results are displayed in the Products TableView. If no product is found, an error message is displayed. Clearing the search field repopulates the table with all available products.
Add Part Form
The form includes the following input fields: part name, inventory level (stock), price, maximum and minimum values, and radio buttons for In-House and Outsourced options.
The bottom label switches to display either "Machine ID" or "Company Name" based on the selected radio button.
The application auto-generates a unique part ID, which is disabled for editing.
After saving the data, the user is redirected to the Main form. Canceling or exiting the form also redirects the user to the Main form.
Modify Part Form
The form displays the selected part's data in the input fields.
The bottom label switches to display either "Machine ID" or "Company Name" based on the part's type (In-House or Outsourced).
The user can modify the data values, except for the part ID.
After saving the modifications, the user is redirected to the Main form. Canceling or exiting the form also redirects the user to the Main form.
Add Product Form
The form includes input fields for product name, inventory level (stock), price, and maximum and minimum values.
The application auto-generates a unique product ID, which is disabled for editing.
The user can search for parts by ID or name using the search field. The matching results are displayed in the top table.
Selected parts can be added to the bottom table, associating them with the product.
The "Remove Associated Part" button removes a selected part from the bottom table.
After saving the data, the user is redirected to the Main form. Canceling or exiting the form also redirects the user to the Main form.
Modify Product Form
The form displays the selected product's data in the input fields.
The user can search for