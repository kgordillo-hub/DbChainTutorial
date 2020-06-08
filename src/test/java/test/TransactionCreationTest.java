package test;

import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;

import com.bigchaindb.builders.BigchainDbConfigBuilder;
import com.bigchaindb.builders.BigchainDbTransactionBuilder;
import com.bigchaindb.constants.Operations;
import com.bigchaindb.model.GenericCallback;
import com.bigchaindb.model.Transaction;
import com.bigchaindb.util.KeyPairUtils;

import co.agro.blockchain.dto.MetaDataDto;
import co.agro.blockchain.dto.ProductDto;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import okhttp3.Response;

public class TransactionCreationTest {

	private static final String privateKey = "MC4CAQAwBQYDK2VwBCIEIHZ//lLM0GJXZbMmxRaBgd9Z86MV2qKBGFH7b8kjSoh2";
	private KeyPair keyPair;
	private ProductDto product;
	private MetaDataDto metaData;
	
	@Before
	public void conectar() {
		BigchainDbConfigBuilder.baseUrl("http://51.143.5.24:9984/").setup();
	}
	
	@Before
	public void keyPairDecodification() {
		keyPair = KeyPairUtils.decodeKeyPair(privateKey);
		System.out.println("Getting keypair from private key...");
		System.out.println("Public key from private key: "
				+ KeyPairUtils.encodePublicKeyInBase58((EdDSAPublicKey) keyPair.getPublic()));

	}
	@Before
	public void createProduct() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		product = new ProductDto();
		product.setCreationDate(sdf.format(new Date()));
		product.setIdProduct("apple88728");
		product.setIdRfidTag("7787920202");
		product.setProductName("Manzana verde");
		product.setProductType("Agro-1552");
	}
	
	@Before
	public void createMetadata() {
		metaData = new MetaDataDto();
		metaData.setProductDescription("Manzana verde extra grande de la huerta 'los veredales'");
		metaData.setProductExpirationDate("2020-05-06");
		metaData.setStatusId("1");
		metaData.setStatusDescription("Recibido en centro de acopio");
		metaData.setTransactionCompany("90029280");
		metaData.setTransactionUsername("mperezg@empresa-acopio.com");
		metaData.setTrazabilityId("1");
	}

	@Test
	public void createTransaccion()
			throws TimeoutException, Exception {
		System.out.println("Creating transaction in the Blockchain...");
		final Transaction transaction = BigchainDbTransactionBuilder.init().addAssets(product, ProductDto.class)
				.operation(Operations.CREATE).addMetaData(metaData)
				.buildAndSign((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate())
				.sendTransaction(handleServerResponse());
		System.out.println("If transaction successfully completed, transaction ID will appear below...");
		System.out.println(transaction.getId());
	}

	public GenericCallback handleServerResponse() {
		// define callback methods to verify response from BigchainDBServer
		GenericCallback callback = new GenericCallback() {

			public void transactionMalformed(Response response) {
				System.out.println("malformed " + response.message());
				onFailure();
			}

			public void pushedSuccessfully(Response response) {
				System.out.println("pushedSuccessfully");
				onSuccess(response);
			}

			public void otherError(Response response) {
				System.out.println("otherError" + response.message());
				onFailure();
			}
		};

		return callback;
	}

	public void onFailure() {
		System.out.println("Transaction failed");
	}

	public void onSuccess(Response response) {
		System.out.println("Transaction posted successfully");
	}

//	@Before
//	public void keyPairCreation() {
//		KeyPairGenerator edDsaKpg = new KeyPairGenerator();
//		keyPair = edDsaKpg.generateKeyPair();
//		System.out.println("KEYPAIR created...");
//		System.out
//				.println("Private key value: " + Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
//		System.out.println("Public key value: " + Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
//	}
}
