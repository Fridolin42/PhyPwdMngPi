## Development Protocoll

### For the Raspberry Pi

- ``sudo raspi-config`` > Interface Options > Serial Port > Activate
- firstly, I created a sender and receiver to test the communication with the UART-Adapter to the Pi
- secondly, I made a modular listener structure for incoming messages over the serial port, and I copied the example
  data
  from the other repo in and linked it to the SerialPortIO
- copied ``SerialazibleEntry`` and ``SerialazibleFolder`` from the other Repo, added the password to the data structure
- PwdFileManager to read/write the raw data from the save file
- Remaining CRUD functions
- Implement the AES Crypto class and encrypt the body of the communication
- key exchange with RSA
- encrypted read and write operations
- button for user input

# <p style="color: red">Notes on use:</p>

- This project is experimental in nature only. I advise against using it as a password manager, as I:
    1. Will not provide support for bugs and security issues.
    2. Log security-critical data (such as transmitted passwords) for debugging purposes.
    3. The program was not developed for stable use.
- A UART adapter is required for use.
    - Please note: Serial communication is disabled by default on a Raspberry Pi.
    - Administrator/root privileges may be required to use serial ports on the client device.
- Manual release by the user via the button must be activated MANUALLY in Main.kt before compiling.
