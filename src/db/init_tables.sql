CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(10),
    hash VARCHAR(128),
    salt VARCHAR(5)
);

CREATE TABLE permissions(
    id INT AUTO_INCREMENT,
    user_id INT,
    res VARCHAR(255),
    role VARCHAR(7)
    check (role in ('READ', 'WRITE', 'EXECUTE')),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE sessions(
    id INT AUTO_INCREMENT,
    user_id VARCHAR(10),
    permission_id INT,
    ds VARCHAR(10),
    de VARCHAR(10),
    vol INT,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (permission_id) REFERENCES permissions(id)
);