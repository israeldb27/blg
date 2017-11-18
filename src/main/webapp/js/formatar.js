
	function formatarMoeda(z){
		v = z.value; 
		v=v.replace(/\D/g,"") // permite digitar apenas numero
		v=v.replace(/(\d{1})(\d{29})$/,"$1.$2") // coloca ponto antes dos ultimos digitos
		v=v.replace(/(\d{1})(\d{26})$/,"$1.$2") // coloca ponto antes dos ultimos digitos
		v=v.replace(/(\d{1})(\d{23})$/,"$1.$2") // coloca ponto antes dos ultimos digitos
		v=v.replace(/(\d{1})(\d{20})$/,"$1.$2") // coloca ponto antes dos ultimos digitos
		v=v.replace(/(\d{1})(\d{17})$/,"$1.$2") // coloca ponto antes dos ultimos digitos
		v=v.replace(/(\d{1})(\d{14})$/,"$1.$2") // coloca ponto antes dos ultimos digitos 
		v=v.replace(/(\d{1})(\d{11})$/,"$1.$2") // coloca ponto antes dos ultimos 13 digitos 
		v=v.replace(/(\d{1})(\d{8})$/,"$1.$2") // coloca ponto antes dos ultimos 10 digitos
		v=v.replace(/(\d{1})(\d{5})$/,"$1.$2") // coloca ponto antes dos ultimos 10 digitos
		v=v.replace(/(\d{1})(\d{2})$/,"$1.$2") // coloca ponto antes dos ultimos 7 digitos
		z.value = v; 
	}
	
	function SomenteNumero(e){
		
	    var tecla=(window.event)?event.keyCode:e.which;   
	    if((tecla>47 && tecla<58)) return true;
	    else{
	    	if (tecla==8 || tecla==0) return true;
		else  return false;
	    }
	}
	
	function mascaraData(campoData){		
		var data = campoData.value;
		if(data.length == 2)
		{
			data += '/';
			campoData.value = data;
			return true;
		}
		if(data.length == 5)
		{
			data += '/';
			campoData.value = data;
			return true;
		}
	}
