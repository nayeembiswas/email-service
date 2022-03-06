/**
 * @Since Jan 25, 2021
 * @Author Nayeemul Islam
 * @Project drug-product-microservice-resourse-server
 * @Package net.ati.product.common.exception
 */
package com.example.demo.auth.exception;

/**
 * @author Nayeem
 *
 */
public class FileStorageException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
