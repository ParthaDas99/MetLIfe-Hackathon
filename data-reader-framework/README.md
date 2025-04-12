
# Data Reader Framework (Java)

## 📌 Description
This Java-based framework reads various file formats (PDF, Excel, XML, HTML) and extracts data into a single `.txt` file.

## ✅ Supported File Types
- PDF
- Excel (`.xlsx`)
- XML
- HTML

## 🧱 Project Structure
```
src/main/java/
├── main/      → Entry point (Main.java)
├── reader/    → File readers
└── utils/     → Output file writer
input/         → Place your files here
output/        → Result will be saved to result.txt
```

## ▶️ How to Run
```bash
# Step 1: Go to project folder
cd data-reader-framework

# Step 2: Build the project
mvn clean install

# Step 3: Run the project
mvn exec:java -Dexec.mainClass="main.Main"
```

## 📦 Dependencies
- Apache PDFBox
- Apache POI (Excel)
- JSoup (HTML)

Enjoy!
