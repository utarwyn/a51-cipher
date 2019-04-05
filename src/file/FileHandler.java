package file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileHandler {

	private File file;

	private FileOutputStream outputStream;

	private String originalFilename;

	public FileHandler(String filename) {
		this.file = new File(filename);
		this.originalFilename = filename;
	}

	public String getPath() {
		return this.file.getAbsolutePath();
	}

	public String getPathWithoutExtension() {
		String path = this.file.getAbsolutePath();
		int lastIndexOf = path.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return path;
		}
		return path.substring(0, lastIndexOf);
	}

	public String getExtension() {
		String name = this.file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return "";
		}
		return name.substring(lastIndexOf);
	}

	public String getOriginalFilename() {
		return this.originalFilename;
	}

	public boolean exists() {
		return this.file.exists();
	}

	public void openOutputStream() throws IOException {
		if (this.file.exists() || this.file.createNewFile()) {
			this.outputStream = new FileOutputStream(this.file);
		}
	}

	public void closeOutputStream() throws IOException {
		if (this.outputStream != null) {
			this.outputStream.close();
		}
	}

	public byte[] readAllBytes() throws IOException {
		return Files.readAllBytes(this.file.toPath());
	}

	public void writeBytes(byte[] data) throws IOException {
		if (this.outputStream != null) {
			this.outputStream.write(data);
		}
	}

}
