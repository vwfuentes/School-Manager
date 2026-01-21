# SOLO si es un repo personal/no compartido
git filter-branch --env-filter '
    OLD_EMAIL="ronald.espinoza@sweet.io"
    CORRECT_NAME="Ronald Espinoza Tordoya"
    CORRECT_EMAIL="ronald.espinoza.to@gmail.com"

    if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ]
    then
        export GIT_COMMITTER_NAME="$CORRECT_NAME"
        export GIT_COMMITTER_EMAIL="$CORRECT_EMAIL"
    fi
    if [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL" ]
    then
        export GIT_AUTHOR_NAME="$CORRECT_NAME"
        export GIT_AUTHOR_EMAIL="$CORRECT_EMAIL"
    fi
' --tag-name-filter cat -- --branches --tags