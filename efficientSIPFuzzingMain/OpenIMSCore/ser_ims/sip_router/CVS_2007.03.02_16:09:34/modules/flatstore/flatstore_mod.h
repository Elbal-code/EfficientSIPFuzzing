/*
 * $Id: flatstore_mod.h 165 2007-03-02 15:15:46Z vingarzan $
 *
 * Flatstore module interface
 *
 * Copyright (C) 2004 FhG Fokus
 *
 * This file is part of ser, a free SIP server.
 *
 * ser is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version
 *
 * For a license to use the ser software under conditions
 * other than those described here, or to purchase support for this
 * software, please contact iptel.org by e-mail at the following addresses:
 *    info@iptel.org
 *
 * ser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
/*
 * History:
 * --------
 *  2003-03-11  updated to the new module exports interface (andrei)
 *  2003-03-16  flags export parameter added (janakj)
 */

#ifndef FLATSTORE_MOD_H
#define FLATSTORE_MOD_H

#include <time.h>

/*
 * Process number used in filenames
 */
extern int flat_pid;

/*
 * Should we flush after each write to the database ?
 */
extern int flat_flush;

/*
 * Delimiter delimiting rows
 */
extern char *flat_record_delimiter;

/*
 * Delimiter delimiting columns
 */
extern char *flat_delimiter;

/*
 * Escape char escaning delimiters and itself
 */
extern char *flat_escape;

/*
 * The timestamp of log rotation request from
 * the FIFO interface
 */
extern time_t* flat_rotate;


/*
 * Local timestamp marking the time of the
 * last log rotation in the process
 */
extern time_t local_timestamp;


#endif /* FLATSTORE_MOD_H */
