package co.agro.blockchain.utils;

import java.security.KeyPair;

import net.i2p.crypto.eddsa.KeyPairGenerator;

public final class Utils {

	public static KeyPair generateKeyPair() {
		final KeyPairGenerator kpgenerator = new KeyPairGenerator();
		return kpgenerator.generateKeyPair();
	}
	
//	sendTransaction(new GenericCallback() {
//  public void transactionMalformed(Response response) {
//      // System.out.println(response.message());
//      System.out.println("malformed " + response.message());
//  }
//
//  public void pushedSuccessfully(Response response) {
//      System.out.println("pushedSuccessfully");
//  }
//
//  public void otherError(Response response) {
//      System.out.println("otherError");
//
//  }
//});
}
