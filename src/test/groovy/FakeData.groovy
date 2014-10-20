
class FakeData {

	def static LABELS_FULL =
	[
		[label:'Podcast-Various', id:'user/userid/category/Podcast-Various'],
		[label:'Podcast-Tech', id:'user/userid/category/Podcast-Tech'],
		[label:'Computing', id:'user/userid/category/Computing'],
		[label:'Programming', id:'user/userid/category/Programming'],
		[label:'Podcast-Selection', id:'user/userid/category/Podcast-Selection'],
		[label:'Podcast-Limbo', id:'user/userid/category/Podcast-Limbo'],
		[label:'Limbo', id:'user/userid/category/Limbo'],
		[label:'Blogs', id:'user/userid/category/Blogs'],
		[label:'Webcomics', id:'user/userid/category/Webcomics']
	]

	def static TAGS_FULL =
	[
		[label:'Forever list', id:'user/userid/tag/Forever list'],
		[label:'Read list', id:'user/userid/tag/Read list']
	]

	def static getPOSTS(String path, String queryString) {
		switch (path) {
			case "categories":
				return FakeData.LABELS_FULL
			case "tags":
				return FakeData.TAGS_FULL
			case "streams/contents":
				def labels = FakeData.LABELS_FULL.clone()
				labels << [label: 'All', id: 'user/userid/category/global.all'];
				labels << [label: 'Saved', id: "user/userid/tag/global.saved"];
				labels << [label: 'Uncategorized', id: 'user/userid/category/global.uncategorized'];
				def maxPosts = Integer.valueOf(queryString.find(/count=(\d+)/) {
					match, maxPosts -> maxPosts
				})
				def streamId = queryString.find(/streamId=(.+)/) {
					match, streamId -> streamId
				}
				if ( maxPosts == 0 )
					return [items:[]]
				if (labels.find{it.id == streamId}?.label == 'Saved') {
					def posts = [
						[id: "EwMb8ndJFt5jpci1CR1uzQ5M8ZbQP8DUG2uIeQLH+SE=_148ad2839ae:e651fa:f38bb8cd",
							origin: [title: "Uploads by Nerdologia"],
							title: "A F\u00edsica nos V\u00eddeo Games | Nerdologia 50",
							summary: [content: """<div>\n<table cellpadding=\"0\" border=\"0\" cellspacing=\"0\"><tbody><tr><td width=\"140\" 
						 		rowspan=\"2\" valign=\"top\"><div><a href=\"http://www.youtube.com/watch?v=fuZU0HLRbWA&amp;feature=youtube_gdata\">
								<img alt=\"\" src=\"http://i.ytimg.com/vi/fuZU0HLRbWA/default.jpg\"></a></div></td>\n<td width=\"256\" valign=\"top\">
								<div><a href=\"http://www.youtube.com/watch?v=fuZU0HLRbWA&amp;feature=youtube_gdata\">A F\u00edsica nos V\u00eddeo Games | Nerdologia 50</a>
								\n<br></div>\n<div><span>TRIALS FUSION Dispon\u00edvel para as seguintes plataformas: -Xbox One: 
								http://goo.gl/3WjSeq -Xbox 360: http://goo.gl/Qm1l2J -PS4: http://goo.gl/AxGwLX -Uplay 
								PC:...</span></div></td>\n<td width=\"146\" valign=\"top\"><div><span>From:</span>\n
								<a href=\"http://www.youtube.com/channel/UClu474HMt895mVxZdlIHXEA\">Nerdologia</a></div>
								\n<div><span>Views:</span>\n303883</div>\n<div>
								<img alt=\"\" align=\"top\" src=\"http://gdata.youtube.com/static/images/icn_star_full_11x11.gif\"> 
								<img alt=\"\" align=\"top\" src=\"http://gdata.youtube.com/static/images/icn_star_full_11x11.gif\"> 
								<img alt=\"\" align=\"top\" src=\"http://gdata.youtube.com/static/images/icn_star_full_11x11.gif\"> 
								<img alt=\"\" align=\"top\" src=\"http://gdata.youtube.com/static/images/icn_star_full_11x11.gif\"> 
								<img alt=\"\" align=\"top\" src=\"http://gdata.youtube.com/static/images/icn_star_half_11x11.gif\">
								</div>\n<div>40700\n<span>ratings</span></div></td></tr>\n<tr><td><span>Time:</span>\n
								<span>05:32</span></td>\n<td><span>More in</span>\n
								<a href=\"http://www.youtube.com/videos?c=28\">Science &amp; Technology</a></td></tr></tbody></table></div>"""],
							 published: 1411653403000,
							 enclosure: null,
							 content: [content: null]
						],
						[id: "zyt0ForLIDnEKh11hg/sHoIQgkjsl4uQUmp3v8z4EAs=_148aed1502e:181b08d:40a25680",
							origin: [title: "Matando Rob\u00f4s Gigantes"],
							title: "MRG 239: S\u00e3o Jorge!",
							summary: [content: """No epis\u00f3dio de hoje,\u00a0Diogo Braga,\u00a0Roberto Estrada\u00a0e o 
									convidado Fabio Catena\u00a0apontam as lan\u00e7as para o cora\u00e7\u00e3o da 
									besta em S\u00e3o Jorge!\u00a0 Ou\u00e7a ou baixe usando os bot\u00f5es 
									abaixo (para baixar, clique com o bot\u00e3o direito do mouse e escolha 
									a op\u00e7\u00e3o\u00a0salvar destino como)! E no MRG de hoje: Danilo est\u00e1 de volta Drag\u00e3o = XP? O [\u2026]"""],
							published: 1411681254000,
							enclosure: [[
									href: "http://jovemnerd.com.br/podpress_trac/feed/102750/0/MRG239_Sao_Jorge.mp3",
									length: 27570642,
									type: "audio/mpeg"
								]],
							content: [content: """<p><a title=\"MRG 239: S\u00e3o Jorge!\" 
									href=\"http://jovemnerd.com.br/matando-robos-gigantes/mrg-239-sao-jorge/\">
									<img height=\"312\" alt=\"SMALL_MRG_239\" width=\"585\" src=\"http://jovemnerd.com.br/wp-content/uploads/SMALL_MRG_239.jpg\">
									</a></p>\n<p><em>No epis\u00f3dio de hoje<em><em><em>,\u00a0<a target=\"_blank\" 
									href=\"http://twitter.com/diogomrg\">Diogo Braga</a>,\u00a0<em><em><a target=\"_blank\" 
									href=\"https://twitter.com/betomrg\">Roberto Estrada</a>\u00a0e o convidado <a target=\"_blank\" 
									href=\"https://twitter.com/fabiocatena\">Fabio Catena</a>\u00a0apontam as lan\u00e7as para o cora\u00e7\u00e3o 
									da besta em S\u00e3o Jorge!\u00a0</em></em></em></em></em></em></p>\n<p><strong>Ou\u00e7a ou baixe usando 
									os bot\u00f5es abaixo (para baixar, clique com o bot\u00e3o direito do mouse e escolha a op\u00e7\u00e3o\u00a0<em>
									salvar destino como</em>)!</strong></p>\n<h3>E no MRG de hoje:</h3>\n<ul>\n<li>Danilo est\u00e1 de volta</li>\n<li>Drag\u00e3o
									 = XP?</li>\n<li>O santo guerreiro</li>\n</ul>\n<h3>Bem-vindo a FUS\u00c3O!! Trials no MRG Show dessa semana:</h3>\n<p>
									<iframe height=\"332\" width=\"590\" src=\"http://www.youtube.com/embed/M1aznREfzzM?list=UUO_Jxh3pRfrPROu-W83-iqQ\">
									</iframe></p>\n<h3>Tamb\u00e9m comentado nesse MRG:</h3>\n<ul>\n<li>
									<a target=\"_blank\" href=\"http://www.paninicomics.com.br/web/guest/productdetail?viewItem=751841\">S\u00e3o Jorge \u2013 Hotsite</a>
									</li>\n<li><a target=\"_blank\" href=\"https://www.youtube.com/watch?v=SLhhP7iknf8&amp;list=PLY5UVpzfmNoH1PibZy6lONsN4XqvjNtfv\">
									Trailer \u2013 O Protetor</a></li>\n<li><a target=\"_blank\" href=\"http://www.sonypictures.com.br/Sony/HotSites/Br/oprotetor/\">O
									 Protetor \u2013 Site</a></li>\n<li><a target=\"_blank\" href=\"https://www.facebook.com/OProtetorBrasil%20\">Fanpage do Protetor</a>
									</li>\n</ul>\n<h3>\u00a0A entrada de hoje foi feita por:</h3>\n<ul>\n<li>Tuana Mesquita (letra), Marcio Holanda (vocais) e 
									<a target=\"_blank\" href=\"https://www.youtube.com/channel/UCRACT8JLjT-_et5oYvB7L8A\">Marcio Mesquita (guitarra, baixo e bateria</a>)
									</li>\n</ul>\n<h3>Siga-nos no Twitter:</h3>\n<ul>\n<li><a title=\"Beto!\" target=\"_blank\" href=\"https://twitter.com/betomrg\">
									@BetoMRG</a></li>\n<li><a title=\"Twitter do Affonso!\" target=\"_blank\" href=\"https://twitter.com/affonsosolano\">@AffonsoSolano
									</a></li>\n<li><a title=\"Diogo!\" target=\"_blank\" href=\"https://twitter.com/DiogoMRG\">@DiogoMRG</a></li>\n</ul>\n<h3>
									Curta nossas p\u00e1ginas oficiais no Facebook:</h3>\n<ul>\n<li><a title=\"MRG no Facebook!\" target=\"_blank\" 
									href=\"http://www.facebook.com/matandorobosgigantes\">http://www.facebook.com/matandorobosgigantes</a></li>\n<li>
									<a title=\"Facebook do Beto!\" target=\"_blank\" href=\"http://www.facebook.com/BetoDuqueEstrada\">http://www.facebook.com/BetoDuqueEstrada</a></li>
									\n<li><a title=\"Facebook do Didi!\" target=\"_blank\" href=\"http://www.facebook.com/DidiBraguinha\">http://www.facebook.com/DidiBraguinha</a></li>\n<li>
									<a title=\"Facebook do Solano!\" target=\"_blank\" href=\"http://www.facebook.com/affonsosolano\">http://www.facebook.com/affonsosolano</a></li>
									\n</ul>\n<h3>E-Mails:</h3>\n<p>D\u00favidas, xingamentos e excuse-mes para\u00a0
									<a title=\"Email do MRG!\" target=\"_blank\" href=\"mailto:matandorobosgigantes@matandorobosgigantes.com\">matandorobosgigantes@matandorobosgigantes.com</a></p>\n
									<h3>Qual a Caixa-Postal do MRG?</h3>\n<ul>\n<li>
									<a title=\"Ol\u00e1, Sr. Carteiro!\" target=\"_blank\" href=\"http://4.bp.blogspot.com/-JAKnfnd7OT4/TyIq64uwpZI/AAAAAAAABso/B4OoTNcgb1Y/s1600/postman.jpg\">
									Caixa Postal 2571 EQS 212/412 CEP: 70.275-970 Brasilia, DF</a></li>\n</ul>"""],
						],
						[id: "NPisdrIKKa06gOkvGAdpQ2D2bSZ/psyOpMa7ToVZ4Yw=_148c65558f0:67ae777:7762fa0c",
							origin: [title: "CBN - Podcast - Jornal da CBN"],
							title: "Jornal da CBN - 30/09/2014 06h",
							summary: [content: "Escrever re\u00fane duas alegrias: falar sozinho e a uma multid\u00e3o"],
							published: 1412070521000,
							enclosure: [[
									href: "http://cbn.globoradio.globo.com/global/podcast.mp3?audio=2014/colunas/cortella_140930.mp3&materiaId=1043235&categoriaId=270",
									length: 488931,
									type: "audio/mpeg"
								]],
							content: [content: null],
						],
						[id: "IvOrOTwXr7LV8RVWOD7z3vkbQtETLjutlYZaYACRIZQ=_148b21b29bb:c10a0a:2773ec9f",
							origin: [title: "The Cloudcast (.net) - Weekly Cloud Computing Podcast"],
							title: "The Cloudcast #163 - Evolution of CoreOS",
							summary: [content: """Brian talks with Alex Polvi (@polvi, CEO of @CoreOSLinux) about the evolution of CoreOS over the past year,
		                            and the breadth of products and services (etcd, systemd, fleet, Docker, Flannel) 
					                that have been built to integrate and augment CoreOS. Music Credit: Nine Inch Nails (www.nin.com)"""],
							published:"1411747200000",
							enclosure: [[
									href: "http://www.buzzsprout.com/3195/207454-the-cloudcast-163-evolution-of-coreos.mp3",
									length: 13449344,
									type: "audio/mpeg"
								]],
							content: [content: null]
						],
						[id: "EyDu9SzR2pdoLVfeufkVhoBxfjIx60y5w1awHwxbV/Y=_148ad79514c:104c0fb:f38bb8cd",
							origin: [title: "Grok Podcast"],
							title: "Epis\u00f3dio 112 - Programando no Escuro",
							summary: [content: """Voltamos para continuar nossa s\u00e9rie <strong>Programando no Escuro</strong>, 
	                                com <a target=\"_blank\" href=\"https://twitter.com/magoolation\">Alexandre Costa aka Magoo</a>, 
								 	que trabalha no Ita\u00fa, e <a target=\"_blank\" href=\"https://twitter.com/lucasradaelli\">Lucas Radaelli</a>, 
								 	que trabalha no Google Brasil. Nesse epis\u00f3dio vamos saber como eles aprenderam a programar sem enxergar, 
								 	como eles fazem para desenvolver interfaces gr\u00e1ficas? E a pergunta que n\u00e3o quer calar: 
								 	d\u00e1 para ser dev front-end sem enxergar? Groke e divirta-se conosco :)""",
								published: 1411603200000,
								enclosure: [[
										href: "http://media.grokpodcast.com/grokpodcast-112-programando-no-escuro.mp3",
										length: 1,
										type: "audio/mpeg"
									]],
								content: [content: null],
							]
						]
					]
					return [items: posts[0..(maxPosts>posts.size?posts.size:maxPosts)-1]]
				}
			default:
				null
		}
	}
}
