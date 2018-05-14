package com.ilirium.basic.db;

import java.io.*;

public interface SchemaVersion extends Serializable {

    String getVersion();

    void setVersion(String version);

}
