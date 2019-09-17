package my.learning;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 * Demonstrate stack trace produced by mismatch between encryption and decryption.
 *
 * encrypt_wrong uses a random keystore, returned by getKeyStore()
 * encrypt & decrypt uses the same keystore, returned by getKeyStore2().
 *
 * saml.jks is produced with:
 *
 * $ openssl pkcs12 -export -out keystore5.p12 -inkey mr2.key -in mr.cert -certfile more.cert
 * $ keytool -importkeystore -srckeystore keystore5.p12 -srcstoretype pkcs12 -destkeystore saml.jks -deststoretype jks
 */
public class RsaTest {

    private final static String PASSWORD = "Awesome123";

    public static void main(String [] args) throws Exception {
        String plaintext = "text";
        byte[] ciphertext = encrypt((plaintext));
        String recoveredPlaintext = decrypt(ciphertext);
        System.out.println("recoveredPlaintext   "+recoveredPlaintext);
    }

    private static byte[] encrypt_wrong(String plaintext) throws Exception {
        KeyStore keyStore = getKeyStore();
        Certificate[] certs = keyStore.getCertificateChain("wso2carbon");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, certs[0].getPublicKey());
        return cipher.doFinal(plaintext.getBytes());
    }

    private static byte [] encrypt(String plaintext) throws Exception {
        KeyStore keyStore = getKeyStore2();
        Certificate[] certs = keyStore.getCertificateChain("1");
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, certs[0].getPublicKey());
        return cipher.doFinal(plaintext.getBytes());
    }

    private static String decrypt(byte [] ciphertext) throws Exception {
        KeyStore keyStore = getKeyStore2();
        PrivateKey privateKey = (PrivateKey) keyStore.getKey("1",
                PASSWORD.toCharArray());

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] cipherbyte=cipher.doFinal(ciphertext);
        return new String(cipherbyte);
    }

    public static KeyStore getKeyStore() throws Exception {
        String file ="AppleConnect.jks";
        KeyStore keyStore = KeyStore.getInstance("JKS");
        String password = "wso2carbon";
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            keyStore.load(in, password.toCharArray());
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return keyStore;
    }

    public static KeyStore getKeyStore2() throws Exception {
        String file ="saml.jks";
        KeyStore keyStore = KeyStore.getInstance("JKS");
        String password = PASSWORD;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            keyStore.load(in, password.toCharArray());
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return keyStore;
    }
}
