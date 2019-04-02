package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileHandler {

	private File file;

	private FileInputStream inputStream;

	private FileOutputStream outputStream;

	public FileHandler(String filename) {
		this.file = new File(filename);
	}

	public long getFileSize() {
		return this.file.length();
	}

	public void open(StreamMode streamMode) throws IOException {
		if (streamMode == StreamMode.INPUT) {
			this.inputStream = new FileInputStream(this.file);
		} else {
			if (this.file.exists() || this.file.createNewFile()) {
				this.outputStream = new FileOutputStream(this.file);
			}
		}
	}

	public byte[] readAllBytes() throws IOException {
		if (this.inputStream != null) {
			return Files.readAllBytes(this.file.toPath());
		}

		return null;
	}

	public void writeBytes(byte[] data) throws IOException {
		if (this.outputStream != null) {
			this.outputStream.write(data);
		}
	}

	public void close() throws IOException {
		if (this.inputStream != null) {
			this.inputStream.close();
		}
		if (this.outputStream != null) {
			this.outputStream.close();
		}
	}

}
