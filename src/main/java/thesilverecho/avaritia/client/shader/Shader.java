package thesilverecho.avaritia.client.shader;

import org.lwjgl.opengl.GL11;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL20.*;

public abstract class Shader
{
	private final String vertFile;
	private final String fragFile;
	protected int vertId, fragId, proId;

	public Shader(String vertFile, String fragFile)
	{
		this.vertFile = readStreamToString(Shader.class.getResourceAsStream("/assets/avaritia/shaders/" + vertFile));
		this.fragFile = readStreamToString(Shader.class.getResourceAsStream("/assets/avaritia/shaders/" + fragFile));

	}

	public void create()
	{
		proId = glCreateProgram();

		vertId = glCreateShader(GL_VERTEX_SHADER);
		fragId = glCreateShader(GL_FRAGMENT_SHADER);

		glShaderSource(vertId, vertFile);
		glCompileShader(vertId);
		if (glGetShaderi(vertId, GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{

			System.err.println("VertErr" + glGetProgramInfoLog(vertId, glGetShaderi(vertId, GL_INFO_LOG_LENGTH)));
			return;
		}
		glShaderSource(fragId, fragFile);
		glCompileShader(fragId);

		if (glGetShaderi(fragId, GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			System.err.println(glGetProgramInfoLog(fragId, glGetShaderi(fragId, GL_INFO_LOG_LENGTH)));
			return;
		}

		glAttachShader(proId, vertId);
		glAttachShader(proId, fragId);

		glLinkProgram(proId);
		if (glGetProgrami(proId, GL_LINK_STATUS) == GL11.GL_FALSE)
		{
			System.err.println(glGetProgramInfoLog(fragId, glGetProgrami(fragId, GL_INFO_LOG_LENGTH)));
			return;
		}

		glValidateProgram(proId);
		if (glGetProgrami(proId, GL_VALIDATE_STATUS) == GL11.GL_FALSE)
		{
			System.err.println(glGetProgramInfoLog(fragId, glGetProgrami(fragId, GL_INFO_LOG_LENGTH)));
			return;
		}

		glDeleteShader(vertId);
		glDeleteShader(fragId);

	}

	public void unBind()
	{
		glUseProgram(0);
	}

	public abstract void bind();

	public void destroy()
	{
		glDeleteProgram(proId);
	}

	private String readStreamToString(InputStream inputStream)
	{
		try
		{
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1)
				result.write(buffer, 0, length);
			return result.toString(StandardCharsets.UTF_8.name());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
