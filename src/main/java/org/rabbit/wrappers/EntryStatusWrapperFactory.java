package org.rabbit.wrappers;

public class EntryStatusWrapperFactory
{
  private static EntryStatusWrapperFactory entryStatusWrapperFactory = null;

  public static EntryStatusWrapperFactory getInstance()
  {
    return (EntryStatusWrapperFactory.entryStatusWrapperFactory = new EntryStatusWrapperFactory());
  }

  public EntryStatusWrapper generateOne()
  {
    EntryStatusWrapper localEntryStatusWrapper = new EntryStatusWrapper();
    return localEntryStatusWrapper;
  }
}