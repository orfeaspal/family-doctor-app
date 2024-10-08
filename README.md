Γενικά για την εφαρμογή

Το σύστημα αποτελείται από τρία μέρη. Front end, back end και database. Το front end υλοποιήθηκε με την τεχνλογία της react js. Το back end υλοποιήθηκε με spring boot. Και η βάση δεδομένων υλοποιήθηκε σε MySql. Επομένως, σε όλες τις περιπτώσεις γίνονται requests από το front end προς το back end , το back end τραβάει τα στοιχεία που χρειάζονται από τη βάση δεδομένων και τα γυρνάει προς το front end όπου και εμφανίζονται στο χρήστη.


Οδηγίες εκτέλεσης εφαρμογής

Front end

1. Βασική προϋπόθεση να είναι εγκατεστημένο το node js στον υπολογιστή σας.
2. Κατευθυνθείτε στο βασικό directory της εφαρμογής όπου υπάρχει το αρχείο “package.json” και ανοίξτε ένα terminal (π.χ. command prompt) και εκτελέστε την εντολή “npm install”.
3. Αφού γίνει το install, εκτελέστε την εντολή “npm start” και η εφαρμογή θα έχει ξεκινήσει να τρέχει στη διεύθυνση localhost:3000.

Back end

1. Βασική προϋπόθεση να είναι εγκατεστημένο το mysql στον υπολογιστή σας.
1. Κατευθυνθείτε στη mysql σας και δημιουργήστε μία κενή βάση με όνομα: family_doctor_db.
2. Στο application.properties που συνήθως βρίσκεται στο μονοπάτι
 …\back_end\Family doctor\src\main\resources πρέπει απαραίτητα πριν τρέξετε την εφαρμογή να βάλετε το username και το password της MySql σας.
3. Εάν εκτελέσετε σωστά όλα τα παραπάνω βήματα και κάνετε build και start την εφαρμογή μέσα στο πρόγραμμα εκτέλεσης, θα δημιουργηθεί και η βάση δεδομένων σας.
4. Για την εισαγωγή του admin στην εφαρμογή, αρχικά θα κάνετε εγγραφή νέου χρήστη στην εφαρμογή και στην συνέχεια μέσω του mysql command line θα τρέξετε την εντολή: UPDATE user SET role = 'ADMIN' WHERE username = 'yourusername'; ,
Όπου “yourusername” βάλτε το username που χρησιμοποιήσατε στην εγγραφή του χρήστη στην εφαρμογή.

**Προτεινόμενο πρόγραμμα εκτέλεσης του back end είναι το intellij.
