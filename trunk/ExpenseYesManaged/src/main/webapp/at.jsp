<form action="myfinances" method="POST">
             <input type="hidden" name="openingBalance" value="12243.51" />
             <table class="form-container">
                             <tr>
                                             <td>
                                                             <label for="description" style="float: left">Description</label><br/>
                                                             <textarea rows="3" cols="20" name="description" id="description"></textarea>
                                             </td>
                             </tr>
                             <tr>
                                             <td>
                                                             <label for="openingBalance" style="float: left">Opening balance</label><br/>
                                                             <span id="openingBalance">Rs. 12,243.51</span>
                                             </td>
                             </tr>
                             <tr>
                                             <td>
                                                             <label for="transactionAmount" style="float: left">Amount</label><br/>
                                                             <input type="text" value="" name="transactionAmount" id="transactionAmount" />
                                             </td>
                             </tr>

                             <tr>
                                             <td>
                                                             <input type="submit" value="Save transaction" />
                                             </td>
                             </tr>
             </table>
</form>