// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: filesystem.proto

package filesystem;

public interface UploadRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:filesystem.UploadRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string filename = 1;</code>
   * @return The filename.
   */
  java.lang.String getFilename();
  /**
   * <code>string filename = 1;</code>
   * @return The bytes for filename.
   */
  com.google.protobuf.ByteString
      getFilenameBytes();

  /**
   * <pre>
   * Puede estar vacío
   * </pre>
   *
   * <code>string directory = 2;</code>
   * @return The directory.
   */
  java.lang.String getDirectory();
  /**
   * <pre>
   * Puede estar vacío
   * </pre>
   *
   * <code>string directory = 2;</code>
   * @return The bytes for directory.
   */
  com.google.protobuf.ByteString
      getDirectoryBytes();

  /**
   * <code>bytes content = 3;</code>
   * @return The content.
   */
  com.google.protobuf.ByteString getContent();

  /**
   * <code>string contentBase64 = 4;</code>
   * @return The contentBase64.
   */
  java.lang.String getContentBase64();
  /**
   * <code>string contentBase64 = 4;</code>
   * @return The bytes for contentBase64.
   */
  com.google.protobuf.ByteString
      getContentBase64Bytes();
}
