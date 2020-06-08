package co.agro.blockchain;

import java.io.IOException;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.bigchaindb.api.TransactionsApi;
import com.bigchaindb.builders.BigchainDbConfigBuilder;
import com.bigchaindb.builders.BigchainDbTransactionBuilder;
import com.bigchaindb.constants.Operations;
import com.bigchaindb.model.FulFill;
import com.bigchaindb.model.GenericCallback;
import com.bigchaindb.model.MetaData;
import com.bigchaindb.model.Transaction;
import com.bigchaindb.model.Transactions;
import com.bigchaindb.util.KeyPairUtils;

import co.agro.blockchain.dto.MetaDataDto;
import co.agro.blockchain.dto.ProductDto;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import okhttp3.Response;

public class Lanzador {
	private static final String privateKey = "MC4CAQAwBQYDK2VwBCIEIHZ//lLM0GJXZbMmxRaBgd9Z86MV2qKBGFH7b8kjSoh2";
	private static KeyPair keyPair;
	private static ProductDto product;
	private static MetaDataDto metaData;
	
	
	public static void main(String[] args) {
		try {

			conectar();
			keyPairDecodification();
			createProduct();
			createMetadata();
			createTransaccion();
			
//			actualizarTransaccion("d35c0fd6cb01f779dbcd103a559770b70db9126dc8ee34cfc3fb11f67af81c77", createMetadata2());
//			getTransaction("fc10aa9ae084a2171a583fc58f9c7051d07a97ca75f3ddac22c6957f1de50aab",createMetadata2);
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void keyPairDecodification() {
		keyPair = KeyPairUtils.decodeKeyPair(privateKey);
		System.out.println("Getting keypair from private key...");
		System.out.println("Public key from private key: "
				+ KeyPairUtils.encodePublicKeyInBase58((EdDSAPublicKey) keyPair.getPublic()));

	}
	
	public static void createProduct() {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		product = new ProductDto();
		product.setCreationDate(sdf.format(new Date()));
		product.setIdProduct("Pinable223312");
		product.setIdRfidTag("1123124412");
		product.setProductName("Pinaple high quality");
		product.setProductType("Agro-1552");
	}
	
	public static void createMetadata() {
		metaData = new MetaDataDto();
		metaData.setProductDescription("Pinaple high quality from 'los veredales' farm");
		metaData.setProductExpirationDate("2020-05-06");
		metaData.setStatusId("1");
		metaData.setStatusDescription("Recibido en centro de acopio");
		metaData.setTransactionCompany("90029280");
		metaData.setTransactionUsername("mperezg@empresa-acopio.com");
		metaData.setTrazabilityId("1");
	}
	
	public static MetaDataDto createMetadata2() {
		metaData = new MetaDataDto();
		metaData.setStatusId("3");
		metaData.setStatusDescription("Distribucion");
		metaData.setTransactionCompany("90029280");
		metaData.setTransactionUsername("mperezg@empresa-acopio.com");
		metaData.setTrazabilityId("10");
		return metaData;
	}
	
	public static void createTransaccion()
			throws TimeoutException, Exception {
		System.out.println("Creating transaction in the Blockchain...");
		final Transaction transaction = BigchainDbTransactionBuilder.init().addAssets(product, ProductDto.class)
				.operation(Operations.CREATE).addMetaData(metaData)
				.buildAndSign((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate())
				.sendTransaction(handleServerResponse());
		System.out.println("If transaction successfully completed, transaction ID will appear below...");
		System.out.println(transaction.getId());
		System.out.println(transaction.toString());
	}
	
	public static void conectar() {
		BigchainDbConfigBuilder.baseUrl("http://52.247.110.11:9984/").setup();
	}

	private static void actualizarTransaccion(String transacId, final MetaDataDto transferMetaData) throws TimeoutException, Exception {
		final FulFill fulFill = new FulFill();
		fulFill.setTransactionId(transacId);
		fulFill.setOutputIndex(0);

//		MetaData transferMetaData = new MetaData();
//		transferMetaData.setMetaData("Estado", "Almacenado");
//		transferMetaData.setMetaData("otra_info", "rfid tag 8819182929");
//		transferMetaData.setMetaData("Nuevo elemento", "info ramdon aaaa");
//		transferMetaData.setMetaData("Nuevo elemento2", "xxxx112");

		Transaction transferTransaction = BigchainDbTransactionBuilder.init().addMetaData(transferMetaData)
				.addInput(null, fulFill, (EdDSAPublicKey) keyPair.getPublic())
				.addOutput("1", (EdDSAPublicKey) keyPair.getPublic()).addAssets("3c0ebe92c67975d987cf26f6bdc174ffd4a185dfbafa5fe0ef2fe60d682f9c64", String.class)
				.addMetaData(transferMetaData).operation(Operations.TRANSFER)
				.buildAndSign((EdDSAPublicKey) keyPair.getPublic(), (EdDSAPrivateKey) keyPair.getPrivate())
				.sendTransaction(handleServerResponse());

		System.out.println(transferTransaction.getId());
	}

	private static void getTransaction(final String assetId) throws IOException {
		Transactions transactions = TransactionsApi.getTransactionsByAssetId(assetId, Operations.TRANSFER);
		for (final Transaction transac : transactions.getTransactions()) {
			System.out.println(transac.getAsset().getData());
		}
	}

	private static GenericCallback handleServerResponse() {
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

	private static void onFailure() {
		System.out.println("Transaction failed");
	}

	private static void onSuccess(Response response) {
		System.out.println("Transaction posted successfully");
	}
}
