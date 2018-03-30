# libraryhelper

Library Helper was made with the idea that users can sign up with their library card ID number. Linking their libraryhelper account with their card will allow them to view all available items in the library, checkout items, search for a specific item, and view currently checked out items along with their check out/return dates. Users can view a history of their activity such as total books checked out, how many of each genre they've checked out, as well as how many times they've returned something late all visually represented in graphs and charts.

## Things to add and fix

- [ ] Allow the setup of admin accounts
  1. Check items back in
  2. Look up card holders and their details (items checked out, their activity, etc)
  3. Suspend card holder accounts/revoke access
- [ ] Add order options when view the available items in the library
- [ ] Allow users to sign up for a library card
- [ ] Allow registration only if the ID a user is trying to sign up with is in the database
- [ ] Implement RFID like system
  1. Allow users to check out items by scanning their barcodes
  2. Allow admins to scan back in items by scanning teir barcodes
  3. Store item data in database with their barcode information

## Built With

* Java Spring
* MySQL
* D3

