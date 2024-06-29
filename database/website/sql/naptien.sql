-- Create the naptien table
CREATE TABLE naptien (
  id INT PRIMARY KEY AUTO_INCREMENT,
  uid VARCHAR(50),
  sotien INT,
  seri VARCHAR(50),
  code VARCHAR(50),
  loaithe VARCHAR(50),
  tinhtrang INT,
  noidung VARCHAR(255),
  tranid VARCHAR(50),
  time INT
);

-- Insert a record into the naptien table
INSERT INTO naptien (uid, sotien, seri, code, loaithe, tinhtrang, noidung, tranid, time)
VALUES ('namenapValue', amountValue, 'serialValue', 'codeValue', 'telcoValue', 0, 'dang nap the', 'requestIdValue', UNIX_TIMESTAMP());