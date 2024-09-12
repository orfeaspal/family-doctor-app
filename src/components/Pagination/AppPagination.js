import {Pagination} from "@mui/material";

const AppPagination = ({total_cards, items_per_page, page, onChange}) => {

    const noOfPages = Math.ceil(total_cards/items_per_page);

    return(
        <div>
            <Pagination count={noOfPages} page={page} onChange={onChange} variant="outlined" color="primary" />
        </div>
    )
}

export default AppPagination;