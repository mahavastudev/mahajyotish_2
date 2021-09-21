<%@taglib prefix="s" uri="/struts-tags"%>

<div class="kundli-chart">
<img class="img-responsive" src="vastuImages/kundli-box.png">
      <div class="rs1"><strong >Mo</strong><span class="in1">1</span></div>
      <div class="rs2"><strong >2</strong><span class="in2">2</span> </div>
      <div class="rs3"><strong >Ke</strong><span class="in3">3</span></div>
      <div class="rs4"><strong>4</strong><span class="in4">4</span></div>
      <div class="rs5"><span class="in5">5</span><strong>5</strong></div>
      <div class="rs6"><strong>6</strong> <span class="in6">6</span></div>
      <div class="rs7"> <span class="in7">7</span><strong>Su</strong></div>
      <div class="rs8"><strong>Ke</strong> <span class="in9">9</span></div>
      <div class="rs9"><span class="in8">8</span><strong>Ve</strong></div>
      <div class="rs10"><span class="in10">10</span> <strong>Mo</strong></div>
      <div class="rs11"><span class="in11">11</span><strong>Mo</strong></div>
      <div class="rs12"><span class="in12">12</span><strong>Su</strong></div>
    </div>
    
    
    <div class="kundli-chart">
      <h4>MahaJyotish Script</h4>
      <table id="dataTable" width="100%" border="0" cellspacing="0" cellpadding="0" class="ui-table ui-responsive">
          <thead>
            <tr>
              <th scope="col">Planet</th>
              <th scope="col">Source</th>
              <th scope="col">Result(NL)</th>
              <th scope="col">Adtnl H</th>
              <th scope="col">SL</th>
              <th scope="col">NL(SL)</th>
              <th scope="col">ADL upto(current)</th>
              <th scope="col">MD upto</th>
            </tr>
          </thead>
          <tbody>
           <s:iterator value="astroData.natalStrengthChart" id="myAstro"
				status="iterStatus">
				<tr>
					<s:iterator value="myAstro">
						<td><s:property />
						</td>
					</s:iterator>
				</tr>
			</s:iterator>          
          </tbody>
        </table>
        <table id="dataTable" width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 25px;" class="ui-table ui-responsive">
          <thead>
            <tr>
              <th scope="col">Rahu</th>
              <th scope="col">Ketu</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>Ketu</td>
              <td>Ketu</td>
            </tr>                
          </tbody>
        </table>
        
      <table id="dataTable" width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 50px;" class="ui-table ui-responsive">
          <h4>Cusp Details</h4>
          <s:iterator value="astroData.cuspHouseAspectDetails" id="cuspDetails">
          <s:iterator value=#cuspDetails.value id="cuspValue">
          <s:property value="#cuspValue.value.planetName" />
          </s:iterator>
          </s:iterator>
          <thead>
            <tr>
              <th scope="col">House Cusp</th>
              <th scope="col">Sign</th>
              <th scope="col">Degree</th>
              <th scope="col">Nakshatra</th>
              <th scope="col">Padam</th>
              <th scope="col">RL</th>
              <th scope="col">NL</th>
              <th scope="col">SL</th>
            </tr>
          </thead>
          <tbody>
            <s:iterator value="astroData.houseDetailHashTable" id="cuspalDetail">
			<tr>
				<td><s:property value="#cuspalDetail.value.houseNumber" /></td>
				<td><s:property value="#cuspalDetail.value.signName" /></td>
				<td><s:property value="#cuspalDetail.value.degree" /></td>
				<td><s:property value="#cuspalDetail.value.nakshatra" /></td>
				<td><s:property value="#cuspalDetail.value.padam" /></td>
				<td><s:property value="#cuspalDetail.value.RL" /></td>
				<td><s:property value="#cuspalDetail.value.NL" /></td>
				<td><s:property value="#cuspalDetail.value.SL" /></td>
			</tr>
			</s:iterator>             
          </tbody>
        </table>
      </div>
      
      
      
  
     
    
    
