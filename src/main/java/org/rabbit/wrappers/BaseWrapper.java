package org.rabbit.wrappers;

import java.io.Serializable;

public class BaseWrapper
  implements Serializable
{
  private String statusMessage;
  private String errorMessage;
  private int noOfRecordsAffected;
  private String errorTrace;

  public String getErrorMessage()
  {
    return this.errorMessage;
  }

  public void setErrorMessage(String paramString)
  {
    this.errorMessage = paramString;
  }

  public String getErrorTrace()
  {
    return this.errorTrace;
  }

  public void setErrorTrace(String paramString)
  {
    this.errorTrace = paramString;
  }

  public String getStatusMessage()
  {
    return this.statusMessage;
  }

  public void setStatusMessage(String paramString)
  {
    this.statusMessage = paramString;
  }

  public int getNoOfRecordsAffected()
  {
    return this.noOfRecordsAffected;
  }

  public void setNoOfRecordsAffected(int paramInt)
  {
    this.noOfRecordsAffected = paramInt;
  }
}

/* Location:           /home/shnmkhktr/expense-yes-managed/WEB-INF/classes/expense-yes-managed.jar
 * Qualified Name:     org.rabbit.wrappers.BaseWrapper
 * Java Class Version: 6 (50.0)
 * JD-Core Version:    0.5.3
 */