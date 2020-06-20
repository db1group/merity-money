insert INTO equipes(nome, descricao)
VALUES ('admins', 'equipe dos admins');

update pessoas
    set equipe_id = 1
    where id = 1;

commit;