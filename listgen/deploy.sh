git config --global user.email 'berkantk3@gmail.com'
git config --global user.name 'Berkant Korkmaz'
git remote set-url origin https://berkantkz:$token@github.com/berkantkz/KLU_Yemek.git
if git status --porcelain | grep -q 'list.json'; then 
	git add list.json
	git commit -am 'update list'
	echo berkantkz | echo $token | git push -f origin HEAD:master	
else
	echo 'No changes detected'
fi
