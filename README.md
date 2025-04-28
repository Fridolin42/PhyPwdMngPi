## Entwicklungsdokumentaion

### seitens des Pi's

- ``sudo raspi-config`` > Interface Options > Serial Port > Activate
- firstly, I created a sender and receiver to test the communication with the UART-Adapter to the Pi
- secondly, I made a modular listener structure for incoming messages over the serial port, and I copied the example data
  from the other repo in and linked it to the SerialPortIO
- copied ``SerialazibleEntry`` and ``SerialazibleFolder`` from the other Repo, added the password to the data structure
- PwdFileManager to read/write the raw data from the save file
- Remaining CRUD functions
- 